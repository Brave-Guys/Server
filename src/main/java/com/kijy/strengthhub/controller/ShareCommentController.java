package com.kijy.strengthhub.controller;

import com.kijy.strengthhub.dto.ShareCommentDto;
import com.kijy.strengthhub.service.ShareCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/share-comments")
@RequiredArgsConstructor
public class ShareCommentController {

    private final ShareCommentService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ShareCommentDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/share/{shareId}")
    public ResponseEntity<?> getByShareId(@PathVariable Long shareId) {
        return ResponseEntity.ok(service.getCommentsByShareId(shareId));
    }
}