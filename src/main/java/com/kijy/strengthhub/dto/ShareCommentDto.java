package com.kijy.strengthhub.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class ShareCommentDto {
    private Long shareId;
    private Long writerId;
    private LocalDate date;
    private String content;
    private String picture;
}