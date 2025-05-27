package com.kijy.strengthhub.repository;

import com.kijy.strengthhub.entity.ShareRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShareRequestRepository extends JpaRepository<ShareRequest, Long> {
    List<ShareRequest> findByMasterId(Long masterId);
    List<ShareRequest> findByUserId(Long userId);
    boolean existsByUserIdAndMasterId(Long userId, Long masterId);
}
