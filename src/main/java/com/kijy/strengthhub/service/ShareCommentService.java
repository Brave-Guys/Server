package com.kijy.strengthhub.service;

import com.kijy.strengthhub.dto.ShareCommentDto;
import com.kijy.strengthhub.dto.ShareCommentResponseDto;
import com.kijy.strengthhub.entity.ShareComment;
import com.kijy.strengthhub.entity.User;
import com.kijy.strengthhub.repository.ShareCommentRepository;
import com.kijy.strengthhub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShareCommentService {

    private final ShareCommentRepository repository;
    private final UserRepository userRepository;

    public ShareComment create(ShareCommentDto dto) {
        ShareComment comment = ShareComment.builder()
                .shareId(dto.getShareId())
                .writerId(dto.getWriterId())
                .date(dto.getDate())
                .content(dto.getContent())
                .picture(dto.getPicture())
                .build();
        return repository.save(comment);
    }

    public List<ShareCommentResponseDto> getCommentsByShareId(Long shareId) {
        return repository.findByShareIdOrderByDateAscCreatedAtAsc(shareId).stream()
                .map(comment -> {
                    User user = userRepository.findById(comment.getWriterId()).orElse(null);

                    String nickname = user != null ? user.getName() : "익명";
                    String profileImgUrl = user != null ? user.getImgUrl() : null;

                    return ShareCommentResponseDto.builder()
                            .id(comment.getId())
                            .writerId(comment.getWriterId())
                            .nickname(nickname)
                            .profileImgUrl(profileImgUrl)
                            .date(comment.getDate())
                            .content(comment.getContent())
                            .picture(comment.getPicture())
                            .createdAt(comment.getCreatedAt())
                            .build();
                }).toList();
    }
}
