package com.kijy.strengthhub.controller;

import com.kijy.strengthhub.dto.MasterRequestDto;
import com.kijy.strengthhub.service.MasterRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apply-master")
public class MasterRequestController {

    private final MasterRequestService service;

    public MasterRequestController(MasterRequestService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> apply(@RequestBody MasterRequestDto dto) {
        service.saveRequest(dto);
        return ResponseEntity.ok("신청이 접수되었습니다.");
    }
}
