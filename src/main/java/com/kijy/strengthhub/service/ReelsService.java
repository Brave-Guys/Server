package com.kijy.strengthhub.service;

import com.kijy.strengthhub.dto.ReelsResponseDto;
import com.kijy.strengthhub.entity.Challenge;
import com.kijy.strengthhub.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReelsService {

    private final ChallengeRepository challengeRepository;

    public List<ReelsResponseDto> getRandomReels() {
        List<Challenge> allChallenges = challengeRepository.findAll();

        List<Challenge> withVideo = allChallenges.stream()
                .filter(c -> c.getVideoUrl() != null && !c.getVideoUrl().isBlank())
                .collect(Collectors.toList());

        Collections.shuffle(withVideo);

        return withVideo.stream()
                .limit(10)
                .map(ReelsResponseDto::from)
                .collect(Collectors.toList());
    }
}
