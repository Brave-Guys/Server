package com.kijy.strengthhub.service;

import com.kijy.strengthhub.dto.ChallengeRequestDto;
import com.kijy.strengthhub.dto.ChallengeResponseDto;
import com.kijy.strengthhub.entity.Challenge;
import com.kijy.strengthhub.entity.User;
import com.kijy.strengthhub.repository.ChallengeRepository;
import com.kijy.strengthhub.repository.UserRepository;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;

    public ChallengeResponseDto save(ChallengeRequestDto dto) {
        Challenge challenge = Challenge.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .videoUrl(dto.getVideoUrl())
                .writerId(dto.getWriterId())
                .createdAt(new Date())
                .endDate(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000L))
                .build();

        Challenge saved = challengeRepository.save(challenge);
        return toDto(saved);
    }

    public ChallengeResponseDto getById(Long id) {
        Challenge challenge = challengeRepository.findById(id).orElseThrow();
        return toDto(challenge);
    }

    public List<ChallengeResponseDto> getAll() {
        return challengeRepository.findAllByOrderByCreatedAtDesc()
                .stream().map(this::toDto).toList();
    }

    private ChallengeResponseDto toDto(Challenge c) {
        String nickname = userRepository.findByUserId(c.getWriterId())
                .map(User::getName).orElse("익명");

        return ChallengeResponseDto.builder()
                .id(c.getId())
                .name(c.getName())
                .description(c.getDescription())
                .videoUrl(c.getVideoUrl())
                .writerId(c.getWriterId())
                .nickname(nickname)
                .createdAt(c.getCreatedAt())
                .endDate(c.getEndDate())
                .build();
    }
}
