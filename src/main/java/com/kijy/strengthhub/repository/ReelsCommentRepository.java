package com.kijy.strengthhub.repository;

import com.kijy.strengthhub.entity.ReelsComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReelsCommentRepository extends JpaRepository<ReelsComment, Long> {
    List<ReelsComment> findByReelsIdOrderByWriteDateAsc(String reelsId);
    void deleteByrcommentId(Long rcommentId);
    @Modifying
    @Query("UPDATE ReelsComment r SET r.likes = r.likes + 1 WHERE r.rcommentId = :postId")
    void incrementLikeCount(@Param("postId") Long postId);

    @Modifying
    @Query("UPDATE ReelsComment r SET r.likes = r.likes - 1 WHERE r.rcommentId = :postId")
    void decrementLikeCount(@Param("postId") Long postId);
}
