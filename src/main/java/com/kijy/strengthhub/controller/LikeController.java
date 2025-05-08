package com.kijy.strengthhub.controller;

import com.kijy.strengthhub.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/toggle")
    public ResponseEntity<?> toggleLike(@RequestBody Map<String, String> body) {
        String userId = body.get("userId");
        Long postId = Long.parseLong(body.get("postId"));
        String postType = body.get("postType");
        String postOrComment = body.get("postOrComment");

        if (userId == null || postId == null || postType == null || postOrComment == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "필수 항목 누락"));
        }

        boolean liked = likeService.toggleLike(userId, postId, postType, postOrComment);
        return ResponseEntity.ok(Map.of("liked", liked));
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkLike(
            @RequestParam String userId,
            @RequestParam Long postId,
            @RequestParam String postType,
            @RequestParam String postOrComment
    ) {
        boolean liked = likeService.checkLike(userId, postId, postType, postOrComment);
        return ResponseEntity.ok(Map.of("liked", liked));
    }
}
