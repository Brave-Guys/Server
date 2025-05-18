package com.kijy.strengthhub.controller;

import com.kijy.strengthhub.dto.ShareRequestDto;
import com.kijy.strengthhub.service.ShareRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/share-requests")
@RequiredArgsConstructor
public class ShareRequestController {

    private final ShareRequestService service;

    @PostMapping
    public ResponseEntity<?> submit(@RequestBody ShareRequestDto dto) {
        service.save(dto);
        return ResponseEntity.ok("신청 완료");
    }
}