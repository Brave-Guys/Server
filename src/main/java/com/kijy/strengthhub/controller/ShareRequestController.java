package com.kijy.strengthhub.controller;

import com.kijy.strengthhub.dto.ShareRequestDto;
import com.kijy.strengthhub.entity.ShareRequest;
import com.kijy.strengthhub.repository.ShareRequestRepository;
import com.kijy.strengthhub.service.ShareRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/share-requests")
@RequiredArgsConstructor
public class ShareRequestController {

    private final ShareRequestService service;
    private final ShareRequestRepository shareRequestRepository;

    @PostMapping
    public ResponseEntity<?> submit(@RequestBody ShareRequestDto dto) {
        service.save(dto);
        return ResponseEntity.ok("신청 완료");
    }

    @GetMapping("/master/{masterId}")
    public ResponseEntity<?> getRequestsForMaster(@PathVariable Long masterId) {
        List<ShareRequest> requests = shareRequestRepository.findByMasterId(masterId);
        return ResponseEntity.ok(requests);
    }
}