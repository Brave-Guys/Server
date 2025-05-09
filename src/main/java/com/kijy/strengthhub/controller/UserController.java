package com.kijy.strengthhub.controller;

import com.kijy.strengthhub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateNickname(@PathVariable Long userId, @RequestBody Map<String, String> body) {
        String nickname = body.get("nickname");
        if (nickname == null || nickname.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "닉네임이 필요합니다."));
        }

        userService.updateNickname(userId, nickname);
        return ResponseEntity.ok(Map.of("message", "닉네임 변경 완료"));
    }
}
