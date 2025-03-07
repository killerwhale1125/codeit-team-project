package com.gathering.gathering.util;

import com.gathering.common.base.exception.BaseException;
import com.gathering.gathering.model.dto.GatheringResponse;
import com.gathering.gathering.model.dto.GatheringResultPageResponse;
import com.gathering.gathering.model.dto.GatheringSearchResponse;
import com.gathering.gathering.model.entity.*;
import com.gathering.gathering.repository.search.GatheringSearchJpaRepository;
import com.gathering.review.model.dto.BookReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.gathering.common.base.response.BaseResponseStatus.UNKNOWN_SEARCH_TYPE;

@Service
@RequiredArgsConstructor
public class GatheringSearchActions {

    private final GatheringSearchJpaRepository gatheringSearchJpaRepository;

    public GatheringSearchResponse convertToGatheringSearchResponse(Slice<Gathering> slice, Set<Long> wishGatheringIds) {
        List<GatheringResponse> gatheringResponses = slice.getContent().stream()
                .map(gathering -> GatheringResponse.fromEntity(gathering, wishGatheringIds.contains(gathering.getId())))
                .collect(Collectors.toList());

        boolean hasNext = slice.hasNext();

        return GatheringSearchResponse.fromEntity(gatheringResponses, hasNext);
    }

    public GatheringSearchResponse convertToGatheringSearchJoinableResponse(Slice<Gathering> slice, Set<Long> wishGatheringIds) {
        List<GatheringResponse> gatheringResponses = slice.getContent().stream()
                .map(gathering -> GatheringResponse.joinableGatherings(gathering, wishGatheringIds.contains(gathering.getId())))
                .collect(Collectors.toList());

        boolean hasNext = slice.hasNext();

        return GatheringSearchResponse.fromEntity(gatheringResponses, hasNext);
    }

    public GatheringSearchResponse convertToMyGatheringPage(Page<Gathering> result, Map<Long, Double> challengeReadingRateMap) {
        List<GatheringResponse> gatheringResponses = result.getContent().stream()
                .map(gathering -> GatheringResponse.myGatheringFromEntity(gathering, challengeReadingRateMap))
                .collect(Collectors.toList());

        return GatheringSearchResponse.fromEntity(gatheringResponses, result.getTotalElements());
    }

    public GatheringSearchResponse convertToIntegratedResultPages(Page<Object[]> gatherings, Page<BookReviewDto> reviews) {
        /**
         * 검색한 모임 리스트 DTO 변환
         */
        List<GatheringResultPageResponse> gatheringResultPageResponses = gatherings.getContent().stream()
                .map(this::convertRowToGatheringResultPageResponse)
                .collect(Collectors.toList());

        return GatheringSearchResponse.integratedResultPages(gatheringResultPageResponses,
                gatherings.getTotalElements(),
                reviews.getContent(),
                reviews.getTotalElements());
    }

    public Page<Object[]> findGatheringsBySearchWordAndType(String searchWord, SearchType searchType, Pageable pageable) {
        Page<Object[]> resultList = null;
        switch (searchType) {
            case TITLE -> resultList = gatheringSearchJpaRepository.findGatheringsBySearchWordAndTypeTitle(searchWord, pageable);
            case CONTENT -> resultList = gatheringSearchJpaRepository.findGatheringsBySearchWordAndTypeContent(searchWord, pageable);
            case BOOK_NAME -> resultList = gatheringSearchJpaRepository.findGatheringsBySearchWordAndTypeBookName(searchWord, pageable);
            default -> throw new BaseException(UNKNOWN_SEARCH_TYPE);
        };

        return resultList;
    }

    public List<Long> convertToGatheringIds(Page<Object[]> gatherings) {
        return Arrays.stream(gatherings.getContent().toArray(new Object[0][]))
                .map(row -> (Long) row[0])
                .collect(Collectors.toList());
    }

    public GatheringSearchResponse convertToGatheringsResultPage(Page<Object[]> gatherings, long totalElements) {
        /**
         * 검색한 모임 리스트 DTO 변환
         */
        List<GatheringResultPageResponse> gatheringResultPageResponses = gatherings.getContent().stream()
                .map(this::convertRowToGatheringResultPageResponse)
                .collect(Collectors.toList());

        return GatheringSearchResponse.gatheringsResultPage(gatheringResultPageResponses, totalElements);
    }

    private GatheringResultPageResponse convertRowToGatheringResultPageResponse(Object[] row) {
        return new GatheringResultPageResponse(
                (Long) row[0],  // GATHERING_ID
                (String) row[1], // NAME
                (Integer) row[2], // CURRENT_CAPACITY
                (Integer) row[3], // MAX_CAPACITY
                GatheringWeek.valueOf((String) row[4]).getWeek(), // GATHERING_WEEK
                GatheringStatus.valueOf((String) row[5]),
                (Date) row[6],
                ReadingTimeGoal.valueOf((String) row[7]).getMinutes(), // READING_TIME_GOAL
                (String) row[8], // IMAGE_URL
                (Long) row[9], // BOOK_ID
                (String) row[10],  // BOOK TITLE
                (String) row[11]   // BOOK IMAGE
        );
    }

    public GatheringSearchResponse convertToReviewsResultPage(Page<BookReviewDto> reviews) {
        return GatheringSearchResponse.reviewsResultPage(reviews.getContent(), reviews.getTotalElements());
    }

}
