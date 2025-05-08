package com.kijy.strengthhub.repository;

import com.kijy.strengthhub.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByCategoryOrderByCreateDateDesc(String category);

    List<Post> findByWriterIdOrderByCreateDateDesc(String writerId);
}
