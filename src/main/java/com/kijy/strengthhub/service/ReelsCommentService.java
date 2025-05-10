package com.kijy.strengthhub.service;

import com.kijy.strengthhub.dto.ReelsCommentRequestDto;
import com.kijy.strengthhub.entity.ReelsComment;
import com.kijy.strengthhub.repository.ReelsCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReelsCommentService {

    private final ReelsCommentRepository repository;

    public ReelsComment registerComment(ReelsCommentRequestDto dto) {
        ReelsComment comment = ReelsComment.builder()
                .reelsId(dto.getReelsId())
                .writerId(dto.getWriterId())
                .parentId(dto.getParentId())
                .content(dto.getContent())
                .likes(0)
                .build();

        return repository.save(comment);
    }
}
