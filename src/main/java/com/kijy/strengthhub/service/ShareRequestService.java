package com.kijy.strengthhub.service;

import com.kijy.strengthhub.dto.ShareRequestDto;
import com.kijy.strengthhub.dto.ShareRequestResponseDto;
import com.kijy.strengthhub.entity.MasterRequest;
import com.kijy.strengthhub.entity.ShareRequest;
import com.kijy.strengthhub.entity.User;
import com.kijy.strengthhub.repository.MasterRequestRepository;
import com.kijy.strengthhub.repository.ShareRequestRepository;
import com.kijy.strengthhub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShareRequestService {

    private final ShareRequestRepository repository;
    private final UserRepository userRepository;
    private final MasterRequestRepository masterRequestRepository;

    public ShareRequest save(ShareRequestDto dto) {
    boolean exists = repository.existsByUserIdAndMasterId(dto.getUserId(), dto.getMasterId());
    if (exists) {
        throw new IllegalStateException("이미 해당 상급자에게 신청을 보냈거나 관계가 형성되어 있습니다.");
    }

    ShareRequest request = ShareRequest.builder()
            .masterId(dto.getMasterId())
            .userId(dto.getUserId())
            .age(dto.getAge())
            .height(dto.getHeight())
            .weight(dto.getWeight())
            .gender(dto.getGender())
            .content(dto.getContent())
            .status("PENDING")
            .build();

    return repository.save(request);
}
    public void cancelAcceptedConnection(Long userId, Long masterId) {
    ShareRequest existing = repository.findByUserIdAndMasterIdAndStatus(userId, masterId, "ACCEPTED")
            .orElseThrow(() -> new IllegalStateException("해당 사용자와 상급자 간에 수락된 관계가 없습니다."));
    repository.delete(existing);
}


    public List<ShareRequestResponseDto> getByUserId(Long userId) {
        return repository.findByUserId(userId).stream()
                .map(r -> {
                    User master = userRepository.findById(r.getMasterId()).orElse(null);
                    String nickname = master != null ? master.getName() : "알 수 없음";
                    String profileImgUrl = master != null ? master.getImgUrl() : null;

                    String parts = masterRequestRepository.findByUserId(r.getMasterId())
                            .map(MasterRequest::getParts)
                            .orElse(null);

                    return ShareRequestResponseDto.builder()
                            .id(r.getId())
                            .masterId(r.getMasterId())
                            .userId(r.getUserId())
                            .nickname(nickname)
                            .profileImgUrl(profileImgUrl)
                            .parts(parts)
                            .age(r.getAge())
                            .height(r.getHeight())
                            .weight(r.getWeight())
                            .gender(r.getGender())
                            .content(r.getContent())
                            .status(r.getStatus())
                            .build();
                })
                .toList();
    }

}
