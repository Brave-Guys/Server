package com.kijy.strengthhub.service;

import com.kijy.strengthhub.dto.MasterRequestDto;
import com.kijy.strengthhub.entity.MasterRequest;
import com.kijy.strengthhub.repository.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MasterRequestService {

    private final MasterRequestRepository repository;

    public MasterRequestService(MasterRequestRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public MasterRequest saveRequest(MasterRequestDto dto) {
        MasterRequest request = new MasterRequest();
        request.setUserId(dto.getUserId());
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

    @Transactional(readOnly = true)
    public List<MasterRequest> getAllRequests() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<MasterRequest> getRequestById(Long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<MasterRequest> getApprovedRequests() {
        return repository.findByStatus("APPROVED");
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void approve(Long id) {
        MasterRequest request = repository.findById(id).orElseThrow();
        request.setStatus("APPROVED");
        repository.save(request);
    }

    @Transactional
    public void reject(Long id) {
        MasterRequest request = repository.findById(id).orElseThrow();
        request.setStatus("REJECTED");
        repository.save(request);
    }
}
