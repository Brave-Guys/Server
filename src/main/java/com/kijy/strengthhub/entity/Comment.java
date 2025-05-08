package com.kijy.strengthhub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long postId;      // 게시글 ID
    private Long writerId;    // 작성자 ID
    private Long parentId;    // 대댓글 용 (nullable)

    private String content;
    private int likes;

    private Date writeDate;
    private Date updatedAt;
}
