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
        Date now = new Date();

        Challenge challenge = Challenge.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .videoUrl(dto.getVideoUrl())
                .writerId(dto.getWriterId())
                .createdAt(now)
                .endDate(new Date(now.getTime() + 7L * 24 * 60 * 60 * 1000))
                .build();

        challengeRepository.save(challenge);
        String nickname = userRepository.findById(dto.getWriterId())
                .map(User::getName)
                .orElse("알 수 없음");

        return ChallengeResponseDto.from(challenge, nickname);
    }

    public ChallengeResponseDto getById(Long id) {
        Challenge challenge = challengeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("챌린지를 찾을 수 없습니다."));

        String nickname = userRepository.findById(challenge.getWriterId())
                .map(User::getName)
                .orElse("알 수 없음");

        return ChallengeResponseDto.from(challenge, nickname);
    }

    public List<ChallengeResponseDto> getAll() {
        return challengeRepository.findAll().stream().map(challenge -> {
            String nickname = userRepository.findById(challenge.getWriterId())
                    .map(User::getName)
                    .orElse("알 수 없음");
            return ChallengeResponseDto.from(challenge, nickname);
        }).toList();
    }

    public ChallengeResponseDto update(Long id, ChallengeRequestDto dto) {
        Challenge challenge = challengeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("챌린지를 찾을 수 없습니다."));

        challenge.setName(dto.getName());
        challenge.setDescription(dto.getDescription());
        challenge.setVideoUrl(dto.getVideoUrl());
        String nickname = userRepository.findById(challenge.getWriterId())
                .map(User::getName)
                .orElse("알 수 없음");

        return ChallengeResponseDto.from(challengeRepository.save(challenge), nickname);
    }

    public void delete(Long id) {
        if (!challengeRepository.existsById(id)) {
            throw new RuntimeException("존재하지 않는 챌린지입니다.");
        }
        challengeRepository.deleteById(id);
    }
}