package com.gathering.review.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gathering.book.model.dto.BookResponse;
import com.gathering.review.model.entitiy.BookReview;
import com.gathering.review.model.entitiy.GatheringReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ReviewDto {

    private BookReviewDto bookReview;
    private GatheringReviewDto gatheringReviewDto;
    private BookResponse bookResponse;
    private List<ReviewCommentDto> commentList;

    public ReviewDto(BookReviewDto bookReviewDto, BookResponse bookResponse, List<ReviewCommentDto> commentDtoList) {
        this.bookReview = bookReviewDto;
        this.bookResponse = bookResponse;
        this.commentList = commentDtoList;
    }
}
