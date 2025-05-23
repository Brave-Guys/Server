package com.kijy.strengthhub.repository;

import com.kijy.strengthhub.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> findAllByOrderByCreatedAtDesc();
}
