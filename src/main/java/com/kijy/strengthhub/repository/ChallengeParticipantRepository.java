package com.kijy.strengthhub.repository;

import com.kijy.strengthhub.entity.ChallengeParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeParticipantRepository extends JpaRepository<ChallengeParticipant, Long> {
    List<ChallengeParticipant> findByChallengeIdOrderByWriteDateAsc(Long challengeId);
    boolean existsByChallengeIdAndWriterId(Long challengeId, Long writerId);
    void deleteByChallengeIdAndWriterId(Long challengeId, Long writerId);
}
