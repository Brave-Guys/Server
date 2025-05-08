package com.kijy.strengthhub.repository;

import com.kijy.strengthhub.entity.Post;
import jakarta.transaction.Transactional;
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
    @Transactional
    @Query("UPDATE Post p SET p.likes = p.likes + 1 WHERE p.id = :id")
    void incrementLikeCount(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Post p SET p.likes = p.likes - 1 WHERE p.id = :id")
    void decrementLikeCount(@Param("id") Long id);

    @Query("SELECT p FROM Post p WHERE p.category <> '공지' ORDER BY p.likes DESC")
    List<Post> findTop3ByLikeCountDescExcludingNotice(Pageable pageable);

    List<Post> findByNameContainingIgnoreCaseOrContentContainingIgnoreCase(String name, String content);
}
