package com.kijy.strengthhub.service;

import com.kijy.strengthhub.dto.CommentRequestDto;
import com.kijy.strengthhub.dto.CommentResponseDto;
import com.kijy.strengthhub.entity.Comment;
import com.kijy.strengthhub.entity.User;
import com.kijy.strengthhub.repository.CommentRepository;
import com.kijy.strengthhub.repository.PostRepository;
import com.kijy.strengthhub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<CommentResponseDto> getComments(Long postId) {
        return commentRepository.findByPostIdOrderByWriteDateAsc(postId).stream()
                .map(comment -> {
                    String nickname = userRepository.findById(comment.getWriterId())
                            .map(User::getName).orElse("익명");
                    return CommentResponseDto.builder()
                            .id(comment.getId())
                            .writerId(comment.getWriterId())
                            .nickname(nickname)
                            .content(comment.getContent())
                            .parentId(comment.getParentId())
                            .likes(comment.getLikes())
                            .writeDate(comment.getWriteDate())
                            .updatedAt(comment.getUpdatedAt())
                            .build();
                }).toList();
    }

    public Comment addComment(Long postId, CommentRequestDto dto) {
        Comment comment = Comment.builder()
                .postId(postId)
                .writerId(dto.getWriterId())
                .content(dto.getContent())
                .parentId(dto.getParentId())
                .likes(0)
                .writeDate(new Date())
                .build();

        // 게시글 댓글 수 증가
        postRepository.findById(postId).ifPresent(post -> {
            post.setCommentCount(post.getCommentCount() + 1);
            postRepository.save(post);
        });

        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow();

        // 게시글 댓글 수 감소
        postRepository.findById(comment.getPostId()).ifPresent(post -> {
            post.setCommentCount(post.getCommentCount() - 1);
            postRepository.save(post);
        });

        commentRepository.deleteById(id);
    }

    public Comment updateComment(Long id, String content) {
        Comment comment = commentRepository.findById(id).orElseThrow();
        comment.setContent(content);
        comment.setUpdatedAt(new Date());
        return commentRepository.save(comment);
    }
}
