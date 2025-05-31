package com.kijy.strengthhub.repository;

import com.kijy.strengthhub.entity.ShareRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShareRequestRepository extends JpaRepository<ShareRequest, Long> {

    // 기존 메서드
    List<ShareRequest> findByMasterId(Long masterId);
    List<ShareRequest> findByUserId(Long userId);
    boolean existsByUserIdAndMasterId(Long userId, Long masterId);

    // ✅ 수락된 관계(ACCEPTED) 여부 확인
    boolean existsByUserIdAndMasterIdAndStatus(Long userId, Long masterId, String status);

    // ✅ 수락된 관계 조회
    Optional<ShareRequest> findByUserIdAndMasterIdAndStatus(Long userId, Long masterId, String status);

    // ✅ 수락된 관계 삭제 (정말 삭제할 경우)
    void deleteByUserIdAndMasterIdAndStatus(Long userId, Long masterId, String status);
}
