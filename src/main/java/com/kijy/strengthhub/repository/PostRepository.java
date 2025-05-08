package com.kijy.strengthhub.repository;

import com.kijy.strengthhub.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByCategoryOrderByCreateDateDesc(String category);
    List<Post> findByWriterIdOrderByCreateDateDesc(Long writerId);
    Page<Post> findByCategory(String category, Pageable pageable);
    Page<Post> findByWriterId(Long writerId, Pageable pageable);
    Page<Post> findByCategoryAndWriterId(String category, Long writerId, Pageable pageable);
    @Modifying
    @Query("UPDATE Post p SET p.likeCount = p.likeCount + 1 WHERE p.id = :id")
    void incrementLikeCount(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Post p SET p.likeCount = p.likeCount - 1 WHERE p.id = :id")
    void decrementLikeCount(@Param("id") Long id);
}
