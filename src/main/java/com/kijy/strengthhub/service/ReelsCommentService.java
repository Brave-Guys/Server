package com.kijy.strengthhub.service;

import com.kijy.strengthhub.dto.ReelsCommentRequestDto;
import com.kijy.strengthhub.dto.ReelsCommentResponseDto;
import com.kijy.strengthhub.entity.ReelsComment;
import com.kijy.strengthhub.entity.User;
import com.kijy.strengthhub.repository.ReelsCommentRepository;
import com.kijy.strengthhub.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReelsCommentService {

    private final ReelsCommentRepository reelsCommentRepository;
    private final UserRepository userRepository;

    public ReelsComment registerComment(ReelsCommentRequestDto dto) {
        ReelsComment comment = ReelsComment.builder()
                .reelsId(dto.getReelsId())
                .writerId(dto.getWriterId())
                .parentId(dto.getParentId())
                .content(dto.getContent())
                .likes(0)
                .build();

        return reelsCommentRepository.save(comment);
    }

    public List<ReelsCommentResponseDto> getCommentsByReelsId(String reelsId) {
        List<ReelsComment> comments = reelsCommentRepository.findByReelsIdOrderByWriteDateAsc(reelsId);
        return comments.stream().map(c -> {
            User user = userRepository.findById(c.getWriterId()).orElse(null);
            return ReelsCommentResponseDto.builder()
                    .rcommentId(c.getRcommentId())
                    .reelsId(c.getReelsId())
                    .writerId(c.getWriterId())
                    .nickname(user != null ? user.getName() : "알 수 없음")
                    .profileImgUrl(user != null ? user.getImgUrl() : null)
                    .content(c.getContent())
                    .parentId(c.getParentId())
                    .writeDate(c.getWriteDate())
                    .build();
        }).toList();
    }

    @Transactional
    public void deleteComment(Long rcommentId) {
        reelsCommentRepository.deleteByrcommentId(rcommentId);
    }

    @Transactional
    public void updateComment(Long rcommentId, String newContent) {
        ReelsComment comment = reelsCommentRepository.findById(rcommentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        comment.setContent(newContent);
    }

}
