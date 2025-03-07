package com.gathering.challenge.repository;

import com.gathering.challenge.model.domain.ChallengeDomain;
import com.gathering.challenge.model.entity.Challenge;
import com.gathering.challengeuser.model.entity.ChallengeUser;

import java.time.LocalDate;
import java.util.List;

public interface ChallengeRepository {
    ChallengeDomain save(ChallengeDomain challenge);

    Challenge getChallengeUsersById(Long id);

    Challenge findById(Long challengeId);

    Challenge findGatheringAndChallengeById(Long challengeId);

    List<Long> findByStartDate(LocalDate today);

    ChallengeUser getChallengeUserByChallengeIdAndUserId(Long challengeId, long userId);

    List<Challenge> getByIdsIn(List<Long> challengeIds);
}
