package com.kijy.strengthhub.controller;

import com.kijy.strengthhub.dto.ChallengeParticipantRequestDto;
import com.kijy.strengthhub.dto.ChallengeParticipantResponseDto;
import com.kijy.strengthhub.entity.ChallengeParticipant;
import com.kijy.strengthhub.service.ChallengeParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/challenge_participants")
@RequiredArgsConstructor
public class ChallengeParticipantController {

    private final ChallengeParticipantService service;

    @PostMapping("/{id}")
    public ResponseEntity<?> participate(@PathVariable("id") Long challengeId, @RequestBody ChallengeParticipantRequestDto dto) {
        dto.setChallengeId(challengeId);
        ChallengeParticipant saved = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{challengeId}")
    public ResponseEntity<List<ChallengeParticipantResponseDto>> getParticipants(@PathVariable Long challengeId) {
        return ResponseEntity.ok(service.getByChallenge(challengeId));
    }

    @GetMapping("/{id}/check")
    public ResponseEntity<Map<String, Boolean>> check(@PathVariable("id") Long challengeId,
                                                      @RequestParam Long writerId) {
        boolean exists = service.checkParticipation(challengeId, writerId);
        return ResponseEntity.ok(Map.of("exists", exists));
    }

    @DeleteMapping("/{challengeId}/{userId}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long challengeId, @PathVariable Long userId) {
        service.delete(challengeId, userId);
        return ResponseEntity.ok(Map.of("message", "수행 내역 삭제 완료"));
    }

    @GetMapping("/{challengeId}/participants/{writerId}")
    public ResponseEntity<ChallengeParticipantResponseDto> getParticipantDetail(@PathVariable Long challengeId,
                                                                                @PathVariable Long writerId) {
        ChallengeParticipantResponseDto response = service.getOne(challengeId, writerId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my")
    public ResponseEntity<List<ChallengeParticipantResponseDto>> getMyChallenges(@RequestParam Long writerId) {
        return ResponseEntity.ok(service.getByWriter(writerId));
    }
}
