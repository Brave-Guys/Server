package com.kijy.strengthhub.service;

import com.kijy.strengthhub.dto.ShareRequestDto;
import com.kijy.strengthhub.entity.ShareRequest;
import com.kijy.strengthhub.repository.ShareRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShareRequestService {

    private final ShareRequestRepository repository;

    public ShareRequest save(ShareRequestDto dto) {
        ShareRequest request = ShareRequest.builder()
                .masterId(dto.getMasterId())
                .userId(dto.getUserId())
                .age(dto.getAge())
                .height(dto.getHeight())
                .weight(dto.getWeight())
                .gender(dto.getGender())
                .content(dto.getContent())
                .build();
        return repository.save(request);
    }
}