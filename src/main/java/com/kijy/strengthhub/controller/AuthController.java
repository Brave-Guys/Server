package com.kijy.strengthhub.controller;

import com.kijy.strengthhub.dto.RegisterRequestDto;
import com.kijy.strengthhub.dto.RegisterResponseDto;
import com.kijy.strengthhub.dto.LoginRequestDto;
import com.kijy.strengthhub.dto.LoginResponseDto;
import com.kijy.strengthhub.entity.User;
import com.kijy.strengthhub.repository.UserRepository;
import com.kijy.strengthhub.security.JwtUtil;
import com.kijy.strengthhub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterRequestDto dto) {
        RegisterResponseDto response = userService.register(dto);
        return ResponseEntity.ok(response);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto dto) {
        User user = userRepository.findByUserId(dto.getUsername())
                .orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("존재하지 않는 계정입니다.");
        }

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다.");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("user_id", user.getUserId());
        claims.put("role", user.getRole());

        String token = jwtUtil.createToken(claims);

        user.setPassword(null); // 비밀번호는 응답에서 제거

        return ResponseEntity.ok(
                LoginResponseDto.builder()
                        .message("로그인 성공")
                        .token(token)
                        .user(user)
                        .build()
        );
    }
}
