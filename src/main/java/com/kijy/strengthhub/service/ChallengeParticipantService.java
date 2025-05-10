package com.kijy.strengthhub.service;

import com.kijy.strengthhub.dto.ChallengeParticipantRequestDto;
import com.kijy.strengthhub.dto.ChallengeParticipantResponseDto;
import com.kijy.strengthhub.entity.ChallengeParticipant;
import com.kijy.strengthhub.entity.User;
import com.kijy.strengthhub.repository.ChallengeParticipantRepository;
import com.kijy.strengthhub.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeParticipantService {

    private final ChallengeParticipantRepository repository;
    private final UserRepository userRepository;

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

    public List<ChallengeParticipantResponseDto> getByChallenge(Long challengeId) {
        List<ChallengeParticipant> list = repository.findByChallengeIdOrderByWriteDateAsc(challengeId);
        return list.stream().map(p -> {
            String nickname = userRepository.findById(p.getWriterId())
                    .map(User::getName)
                    .orElse("알 수 없음");

            return ChallengeParticipantResponseDto.builder()
                    .id(p.getId())
                    .challengeId(p.getChallengeId())
                    .writerId(p.getWriterId())
                    .nickname(nickname)
                    .content(p.getContent())
                    .videoUrl(p.getVideoUrl())
                    .writeDate(p.getWriteDate())
                    .build();
        }).toList();
    }

    public boolean checkParticipation(Long challengeId, Long writerId) {
        return repository.existsByChallengeIdAndWriterId(challengeId, writerId);
    }

    @Transactional
    public void delete(Long challengeId, Long writerId) {
        repository.deleteByChallengeIdAndWriterId(challengeId, writerId);
    }
}
