package com.kijy.strengthhub.repository;

import com.kijy.strengthhub.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("SELECT l FROM Like l WHERE l.userId = :userId AND l.postId = :postId AND l.postType = :postType AND l.postOrComment = :postOrComment")
    Optional<Like> findByUserIdAndPostIdAndPostTypeAndPostOrComment(
            String userId, Long postId, String postType, String postOrComment
    );

    @Transactional
    @Modifying
    @Query("DELETE FROM Like l WHERE l.userId = :userId AND l.postId = :postId AND l.postType = :postType AND l.postOrComment = :postOrComment")
    void deleteByCompositeKey(
            String userId, Long postId, String postType, String postOrComment
    );

    @Query("SELECT COUNT(l) > 0 FROM Like l WHERE l.userId = :userId AND l.postId = :postId AND l.postType = :postType AND l.postOrComment = :postOrComment")
    boolean existsByUserIdAndPostIdAndPostTypeAndPostOrComment(
            String userId, Long postId, String postType, String postOrComment
    );
}
