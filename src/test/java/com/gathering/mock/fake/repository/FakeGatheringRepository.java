package com.gathering.mock.fake.repository;

import com.gathering.gathering.model.domain.GatheringDomain;
import com.gathering.gathering.model.entity.Gathering;
import com.gathering.gathering.model.entity.GatheringUserStatus;
import com.gathering.gathering.repository.GatheringRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import static com.gathering.gathering.model.entity.GatheringStatus.RECRUITING;
import static com.gathering.gathering.model.entity.GatheringWeek.ONE_WEEK;

public class FakeGatheringRepository implements GatheringRepository {

    private final AtomicLong autoGeneratedId = new AtomicLong(0);
    private List<GatheringDomain> data = new ArrayList<>();

    @Override
    public GatheringDomain save(GatheringDomain gathering) {
        if(Objects.isNull(gathering.getId())) {
            GatheringDomain createGathering = GatheringDomain.builder()
                    .id(autoGeneratedId.incrementAndGet())
                    .name(gathering.getName())
                    .content(gathering.getContent())
                    .startDate(gathering.getStartDate())
                    .endDate(gathering.getEndDate())
                    .minCapacity(gathering.getMinCapacity())
                    .maxCapacity(gathering.getMaxCapacity())
                    .currentCapacity(gathering.getCurrentCapacity())
                    .owner(gathering.getOwner())
                    .viewCount(gathering.getViewCount())
                    .gatheringStatus(gathering.getGatheringStatus())
                    .gatheringWeek(gathering.getGatheringWeek())
                    .book(gathering.getBook())
                    .challenge(gathering.getChallenge())
                    .image(gathering.getImage())
                    .gatheringUsers(new ArrayList<>())
                    .build();
            data.add(createGathering);
            return createGathering;
        } else {
            data.removeIf(item -> Objects.equals(item.getId(), gathering.getId()));
            data.add(gathering);
            return gathering;
        }
    }

    @Override
    public Gathering getById(Long gatheringId) {
        return null;
    }

    @Override
    public void delete(Gathering gathering) {

    }

    @Override
    public Gathering findGatheringWithUsersByIdAndStatus(Long gatheringId, GatheringUserStatus gatheringStatus) {
        return null;
    }

    @Override
    public Gathering getGatheringAndGatheringUsersById(Long gatheringId) {
        return null;
    }

    @Override
    public List<Gathering> findByIdIn(List<Long> gatheringIds) {
        return null;
    }

    @Override
    public Long findIdById(Long gatheringId) {
        return null;
    }

    @Override
    public long getActiveAndParticipatingCount(long id) {
        return 0;
    }

    @Override
    public long getCompletedCount(long id) {
        return 0;
    }

    @Override
    public long getMyCreatedCount(String userName) {
        return 0;
    }

    @Override
    public long getMyWishedCountByGatheringIds(Set<Long> wishGatheringIds) {
        return 0;
    }
}
