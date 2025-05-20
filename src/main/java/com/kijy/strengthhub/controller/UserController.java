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

    @PutMapping("/{id}/image")
    public ResponseEntity<?> updateProfileImage(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String imgUrl = body.get("imgUrl");
        userService.updateProfileImage(id, imgUrl);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<?> updateUserRole(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String role = body.get("role");
        if (role == null || role.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "역할 값이 필요합니다."));
        }

        userService.updateUserRole(id, role);
        return ResponseEntity.ok(Map.of("message", "권한 변경 완료"));
    }

    @GetMapping("/seniors")
    public ResponseEntity<?> getSeniorUsers() {
        return ResponseEntity.ok(userService.getSeniorUsers());
    }


    @PatchMapping("/{id}/plan")
    public ResponseEntity<?> updateUserPlanType(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String plan = body.get("userPlanType");
        if (plan == null || plan.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "플랜 정보가 필요합니다."));
        }

        userService.updateUserPlanType(id, plan);
        return ResponseEntity.ok(Map.of("message", "플랜 변경 완료"));
    }

}
