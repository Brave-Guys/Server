package com.kijy.strengthhub.controller;

import com.kijy.strengthhub.dto.ReelsCommentRequestDto;
import com.kijy.strengthhub.entity.ReelsComment;
import com.kijy.strengthhub.service.ReelsCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reels_comments")
@RequiredArgsConstructor
public class ReelsCommentController {

    private final ReelsCommentService service;

    @PostMapping
    public ResponseEntity<ReelsComment> register(@RequestBody ReelsCommentRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registerComment(dto));
    }
}
