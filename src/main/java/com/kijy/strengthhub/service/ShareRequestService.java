package com.kijy.strengthhub.service;

import com.kijy.strengthhub.dto.ShareRequestDto;
import com.kijy.strengthhub.dto.ShareRequestResponseDto;
import com.kijy.strengthhub.entity.ShareRequest;
import com.kijy.strengthhub.entity.User;
import com.kijy.strengthhub.repository.ShareRequestRepository;
import com.kijy.strengthhub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShareRequestService {

    private final ShareRequestRepository repository;
    private final UserRepository userRepository;

    public ShareRequest save(ShareRequestDto dto) {
    // 중복 관계 확인
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

    public List<ShareRequestResponseDto> getByUserId(Long userId) {
        return repository.findByUserId(userId).stream()
                .map(r -> {
                    User master = userRepository.findById(r.getMasterId()).orElse(null);
                    String nickname = master != null ? master.getName() : "알 수 없음";
                    String profileImgUrl = master != null ? master.getImgUrl() : null;

                    return ShareRequestResponseDto.builder()
                            .id(r.getId())
                            .masterId(r.getMasterId())
                            .userId(r.getUserId())
                            .nickname(nickname)
                            .profileImgUrl(profileImgUrl)
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
