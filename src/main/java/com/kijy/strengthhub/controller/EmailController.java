package com.kijy.strengthhub.controller;

import com.kijy.strengthhub.dto.UserResponseDto;
import com.kijy.strengthhub.entity.User;
import com.kijy.strengthhub.repository.UserRepository;
import com.kijy.strengthhub.service.EmailService;
import com.kijy.strengthhub.store.AuthCodeStore;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    private final AuthCodeStore authCodeStore;
    private final UserRepository userRepository;

    @PostMapping("/send")
    public ResponseEntity<String> sendCode(@RequestParam String email) {
        String code = emailService.generateCode();
        String content = "인증 코드는 [" + code + "] 입니다. 5분 안에 입력해주세요.";

        emailService.sendSimpleMessage(email, "이메일 인증", content);
        authCodeStore.save(email, code);

        return ResponseEntity.ok("이메일 전송 완료");
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(@RequestParam String email, @RequestParam String code) {
        if (!authCodeStore.verify(email, code)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 실패: 코드가 일치하지 않거나 만료됨");
        }

        authCodeStore.remove(email);

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 이메일로 가입된 사용자가 없습니다.");
        }

        User user = userOpt.get();
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
}
