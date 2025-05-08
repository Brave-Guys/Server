package com.kijy.strengthhub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "likes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;           // 사용자 ID
    private Long postId;             // 게시글 or 댓글 ID
    private String postType;         // 예: community, challenge 등
    private String postOrComment;    // post or comment

    private LocalDateTime createdAt;
}
