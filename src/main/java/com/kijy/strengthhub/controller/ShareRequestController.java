package com.kijy.strengthhub.controller;

import com.kijy.strengthhub.dto.ShareRequestDto;
import com.kijy.strengthhub.entity.ShareRequest;
import com.kijy.strengthhub.repository.ShareRequestRepository;
import com.kijy.strengthhub.service.ShareRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        return shareRequestRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String status = body.get("status");
        return shareRequestRepository.findById(id)
                .map(r -> {
                    r.setStatus(status);
                    shareRequestRepository.save(r);
                    return ResponseEntity.ok("상태 업데이트 완료");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(shareRequestRepository.findByUserId(userId));
    }
}