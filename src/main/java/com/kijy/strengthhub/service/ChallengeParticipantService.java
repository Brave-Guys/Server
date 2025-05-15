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

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
            User user = userRepository.findById(p.getWriterId()).orElse(null);
            String nickname = user != null ? user.getName() : "알 수 없음";
            String profileImgUrl = user != null ? user.getImgUrl() : null;

            return ChallengeParticipantResponseDto.builder()
                    .id(p.getId())
                    .challengeId(p.getChallengeId())
                    .writerId(p.getWriterId())
                    .profileImgUrl(profileImgUrl)
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

    public ChallengeParticipantResponseDto getOne(Long participantId) {
        ChallengeParticipant p = repository.findById(participantId)
                .orElseThrow(() -> new RuntimeException("참가자를 찾을 수 없습니다."));

        User user = userRepository.findById(p.getWriterId()).orElse(null);
        String nickname = user != null ? user.getName() : "알 수 없음";
        String profileImgUrl = user != null ? user.getImgUrl() : null;
        
        return ChallengeParticipantResponseDto.builder()
                .id(p.getId())
                .challengeId(p.getChallengeId())
                .writerId(p.getWriterId())
                .profileImgUrl(profileImgUrl)
                .nickname(nickname)
                .content(p.getContent())
                .videoUrl(p.getVideoUrl())
                .writeDate(p.getWriteDate())
                .build();
    }


    public List<ChallengeParticipantResponseDto> getByWriter(Long writerId) {
        List<ChallengeParticipant> list = repository.findByWriterIdOrderByWriteDateDesc(writerId);
        return list.stream().map(p -> {
            User user = userRepository.findById(p.getWriterId()).orElse(null);
            String nickname = user != null ? user.getName() : "알 수 없음";
            String profileImgUrl = user != null ? user.getImgUrl() : null;

            return ChallengeParticipantResponseDto.builder()
                    .id(p.getId())
                    .challengeId(p.getChallengeId())
                    .writerId(p.getWriterId())
                    .nickname(nickname)
                    .profileImgUrl(profileImgUrl)
                    .content(p.getContent())
                    .videoUrl(p.getVideoUrl())
                    .writeDate(p.getWriteDate())
                    .build();
        }).toList();
    }

    public ChallengeParticipant getRandomParticipant() {
        List<ChallengeParticipant> participants = repository.findAllRandom();
        if (participants.isEmpty()) {
            return null;
        }
        // 랜덤으로 하나의 참가자를 선택
        Collections.shuffle(participants);
        return participants.get(0); // 첫 번째 참가자 반환
    }
}
