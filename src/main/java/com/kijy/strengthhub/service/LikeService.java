package com.kijy.strengthhub.service;

import com.kijy.strengthhub.entity.Like;
import com.kijy.strengthhub.repository.CommentRepository;
import com.kijy.strengthhub.repository.LikeRepository;
import com.kijy.strengthhub.repository.PostRepository;
import com.kijy.strengthhub.repository.ReelsCommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ReelsCommentRepository reelsCommentRepository;

    @Transactional
    public boolean toggleLike(String userId, Long postId, String postType, String postOrComment) {
        Optional<Like> existing = likeRepository.findByUserIdAndPostIdAndPostTypeAndPostOrComment(
                userId, postId, postType, postOrComment);

        boolean isPost = postOrComment.equals("post");
        boolean isComment = postOrComment.equals("comment");
        boolean isChallenge = postType.equals("challenge");

        System.out.println("!!!");
        System.out.println(isChallenge);
        System.out.println("!!!");

        if (existing.isPresent()) {
            likeRepository.delete(existing.get());
            if (isPost) {
                if (isComment) {
                    commentRepository.decrementLikeCount(postId);
                } else {
                    postRepository.decrementLikeCount(postId);
                }
            }
             else if (isChallenge) {
                reelsCommentRepository.decrementLikeCount(postId);
            }
            return false;
        } else {
            likeRepository.save(Like.builder()
                    .userId(userId)
                    .postId(postId)
                    .postType(postType)
                    .postOrComment(postOrComment)
                    .createdAt(LocalDateTime.now())
                    .build());

            if (isPost) {
                if (isComment) {
                    commentRepository.incrementLikeCount(postId);
                } else {
                    postRepository.incrementLikeCount(postId);
                }
            }
            else if (isChallenge){
                reelsCommentRepository.incrementLikeCount(postId);
            }
            return true;
        }
    }

    public boolean checkLike(String userId, Long postId, String postType, String postOrComment) {
        return likeRepository.existsByUserIdAndPostIdAndPostTypeAndPostOrComment(
                userId, postId, postType, postOrComment);
    }
}
