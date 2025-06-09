package com.kijy.strengthhub.controller;

import com.kijy.strengthhub.service.EmailService;
import com.kijy.strengthhub.store.AuthCodeStore;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    private final AuthCodeStore authCodeStore;

    @PostMapping("/send")
    public ResponseEntity<String> sendCode(@RequestParam String email) {
        String code = emailService.generateCode();
        String content = "인증 코드는 [" + code + "] 입니다. 5분 안에 입력해주세요.";

        emailService.sendSimpleMessage(email, "이메일 인증", content);
        authCodeStore.save(email, code);
        return ResponseEntity.ok("이메일 전송 완료");
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyCode(@RequestParam String email, @RequestParam String code) {
        boolean isValid = authCodeStore.verify(email, code);
        if (isValid) {
            authCodeStore.remove(email);
            return ResponseEntity.ok("인증 성공");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 실패");
    }
}
