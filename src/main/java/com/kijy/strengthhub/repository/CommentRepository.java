package com.kijy.strengthhub.repository;

import com.kijy.strengthhub.entity.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdOrderByWriteDateAsc(Long postId);
    @Modifying
    @Transactional
    @Query("UPDATE Comment c SET c.likes = c.likes + 1 WHERE c.id = :id")
    void incrementLikeCount(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Comment c SET c.likes = c.likes - 1 WHERE c.id = :id")
    void decrementLikeCount(@Param("id") Long id);
}
