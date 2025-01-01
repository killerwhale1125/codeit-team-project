package com.gathering.challenge.repository;

import com.gathering.challenge.model.entity.Challenge;
import com.gathering.common.base.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.gathering.common.base.response.BaseResponseStatus.NON_EXISTED_CHALLENGE;

@Repository
@RequiredArgsConstructor
public class ChallengeRepositoryImpl implements ChallengeRepository {

    private final ChallengeJpaRepository challengeJpaRepository;

    @Override
    public void save(Challenge challenge) {
        challengeJpaRepository.save(challenge);
    }

    @Override
    public Challenge getChallengeUsersById(Long challengeId) {
        return challengeJpaRepository.getChallengeUsersById(challengeId).orElseThrow(() -> new BaseException(NON_EXISTED_CHALLENGE));
    }

    @Override
    public Challenge findById(Long challengeId) {
        return challengeJpaRepository.findById(challengeId).orElseThrow(() -> new BaseException(NON_EXISTED_CHALLENGE));
    }

    @Override
    public Challenge findGatheringAndChallengeById(Long challengeId) {
        return challengeJpaRepository.findGatheringAndChallengeById(challengeId).orElseThrow(() -> new BaseException(NON_EXISTED_CHALLENGE));
    }

    @Override
    public List<Long> findByStartDate(LocalDate today) {
        return challengeJpaRepository.findByStartDate(today);
    }

}
