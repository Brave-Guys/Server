package com.kijy.strengthhub.repository;

import com.kijy.strengthhub.entity.ReelsComment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReelsCommentRepository extends JpaRepository<ReelsComment, String> {
    List<ReelsComment> findByReelsIdOrderByWriteDateAsc(String reelsId);
}
