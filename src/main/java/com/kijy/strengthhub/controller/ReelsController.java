package com.kijy.strengthhub.controller;

import com.kijy.strengthhub.dto.ReelsResponseDto;
import com.kijy.strengthhub.entity.Challenge;
import com.kijy.strengthhub.repository.ChallengeRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/reels")
@RequiredArgsConstructor
public class ReelsController {

    private final ChallengeRepository challengeRepository;

    @GetMapping
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

