package com.kijy.strengthhub.controller;

import com.kijy.strengthhub.dto.RegisterRequestDto;
import com.kijy.strengthhub.dto.RegisterResponseDto;
import com.kijy.strengthhub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterRequestDto dto) {
        RegisterResponseDto response = userService.register(dto);
        return ResponseEntity.ok(response);
    }
}
