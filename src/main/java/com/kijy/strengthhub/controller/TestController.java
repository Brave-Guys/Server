package com.kijy.strengthhub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("서버 정상 작동 중 (pong)");
    }

    @GetMapping("/")
    public String root() {
        return "서버 루트 접속 성공!";
    }
}
