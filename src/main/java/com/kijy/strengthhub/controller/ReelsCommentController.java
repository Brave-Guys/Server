package com.kijy.strengthhub.controller;

import com.kijy.strengthhub.dto.ReelsCommentRequestDto;
import com.kijy.strengthhub.dto.ReelsCommentResponseDto;
import com.kijy.strengthhub.entity.ReelsComment;
import com.kijy.strengthhub.service.ReelsCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reels_comments")
@RequiredArgsConstructor
public class ReelsCommentController {

    private final ReelsCommentService reelsCommentService;

    @PostMapping
    public ResponseEntity<ReelsComment> register(@RequestBody ReelsCommentRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reelsCommentService.registerComment(dto));
    }

    @GetMapping("/{reelsId}")
    public ResponseEntity<List<ReelsCommentResponseDto>> getComments(@PathVariable String reelsId) {
        List<ReelsCommentResponseDto> comments = reelsCommentService.getCommentsByReelsId(reelsId);
        return ResponseEntity.ok(comments);
    }

}
