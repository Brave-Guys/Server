package com.kijy.strengthhub.repository;

import com.kijy.strengthhub.entity.ChallengeParticipant;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

import java.util.List;


public interface ChallengeParticipantRepository extends JpaRepository<ChallengeParticipant, Long> {
    List<ChallengeParticipant> findByChallengeIdOrderByWriteDateAsc(Long challengeId);
    List<ChallengeParticipant> findByWriterIdOrderByWriteDateDesc(Long writerId);
    boolean existsByChallengeIdAndWriterId(Long challengeId, Long writerId);

    @Modifying
    @Transactional
    void deleteByChallengeIdAndWriterId(Long challengeId, Long writerId);

    Optional<ChallengeParticipant> findByChallengeIdAndWriterId(Long challengeId, Long writerId);

    @Query("SELECT c FROM ChallengeParticipant c ORDER BY FUNCTION('RAND')")
    List<ChallengeParticipant> findAllRandom();

}
