package com.kijy.strengthhub.service;

import com.kijy.strengthhub.dto.MasterRequestDto;
import com.kijy.strengthhub.entity.MasterRequest;
import com.kijy.strengthhub.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class MasterRequestService {

    private final MasterRequestRepository repository;

    public MasterRequestService(MasterRequestRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public MasterRequest saveRequest(MasterRequestDto dto) {
        MasterRequest request = new MasterRequest();
        request.setName(dto.getName());
        request.setPhone(dto.getPhone());
        request.setCareer(dto.getCareer());
        request.setIntro(dto.getIntro());
        request.setLink(dto.getLink());
        request.setParts(String.join(",", dto.getParts()));
        request.setCertFileUrls(String.join(",", dto.getCertFileUrls()));
        request.setPortfolioUrls(String.join(",", dto.getPortfolioUrls()));
        return repository.save(request);
    }
}
