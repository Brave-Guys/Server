package com.kijy.strengthhub.service;

import com.kijy.strengthhub.dto.ChallengeParticipantRequestDto;
import com.kijy.strengthhub.entity.ChallengeParticipant;
import com.kijy.strengthhub.repository.ChallengeParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeParticipantService {

    private final ChallengeParticipantRepository repository;

    public ChallengeParticipant create(ChallengeParticipantRequestDto dto) {
        ChallengeParticipant participant = ChallengeParticipant.builder()
                .challengeId(dto.getChallengeId())
                .writerId(dto.getWriterId())
                .content(dto.getContent())
                .videoUrl(dto.getVideoUrl())
                .writeDate(new Date())
                .build();
        return repository.save(participant);
    }

    public List<ChallengeParticipant> getByChallenge(Long challengeId) {
        return repository.findByChallengeIdOrderByWriteDateAsc(challengeId);
    }

    public boolean checkParticipation(Long challengeId, Long writerId) {
        return repository.existsByChallengeIdAndWriterId(challengeId, writerId);
    }

    public void delete(Long challengeId, Long writerId) {
        repository.deleteByChallengeIdAndWriterId(challengeId, writerId);
    }
}
