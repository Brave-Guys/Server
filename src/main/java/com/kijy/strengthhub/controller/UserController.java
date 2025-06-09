package com.kijy.strengthhub.controller;

import com.kijy.strengthhub.dto.UserResponseDto;
import com.kijy.strengthhub.entity.User;
import com.kijy.strengthhub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserInfo(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserResponseDto dto = UserResponseDto.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .nickname(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .userPlanType(user.getUserPlanType())
                .profileImgUrl(user.getImgUrl())
                .build();

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");

        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "인증된 사용자가 존재하지 않습니다."));
        }

        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

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

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String newPassword = request.get("newPassword");

        try {
            userService.updatePasswordByEmail(email, newPassword);
            return ResponseEntity.ok("비밀번호가 변경되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
