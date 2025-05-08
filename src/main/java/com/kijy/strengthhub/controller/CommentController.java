package com.kijy.strengthhub.controller;

import com.kijy.strengthhub.dto.CommentRequestDto;
import com.kijy.strengthhub.dto.CommentResponseDto;
import com.kijy.strengthhub.entity.Comment;
import com.kijy.strengthhub.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getComments(postId));
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable Long postId, @RequestBody CommentRequestDto dto) {
        return ResponseEntity.ok(commentService.addComment(postId, dto));
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(commentService.updateComment(id, body.get("content")));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok(Map.of("message", "댓글 삭제 완료"));
    }
}
