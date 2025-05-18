package com.kijy.strengthhub.service;

import com.kijy.strengthhub.dto.ShareCommentDto;
import com.kijy.strengthhub.entity.ShareComment;
import com.kijy.strengthhub.repository.ShareCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShareCommentService {

    private final ShareCommentRepository repository;

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

    public List<ShareComment> getCommentsByShareId(Long shareId) {
        return repository.findByShareIdOrderByDateAscCreatedAtAsc(shareId);
    }
}