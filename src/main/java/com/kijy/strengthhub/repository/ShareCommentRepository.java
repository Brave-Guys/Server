package com.kijy.strengthhub.repository;

import com.kijy.strengthhub.entity.ShareComment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ShareCommentRepository extends JpaRepository<ShareComment, Long> {
    List<ShareComment> findByShareIdOrderByDateAscCreatedAtAsc(Long shareId);
}