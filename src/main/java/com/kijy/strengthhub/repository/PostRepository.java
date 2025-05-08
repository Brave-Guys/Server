package com.kijy.strengthhub.repository;

import com.kijy.strengthhub.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByCategoryOrderByCreateDateDesc(String category);
    List<Post> findByWriterIdOrderByCreateDateDesc(Long writerId);
    Page<Post> findByCategory(String category, Pageable pageable);
    Page<Post> findByWriterId(Long writerId, Pageable pageable);
    Page<Post> findByCategoryAndWriterId(String category, Long writerId, Pageable pageable);

}
