package com.kijy.strengthhub.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReelsComment {

    @Id
    private String rcommentId;

    private String reelsId;

    private Long writerId;

    private String parentId; // null이면 원댓글

    @Column(columnDefinition = "TEXT")
    private String content;

    private int likes;

    private LocalDateTime writeDate;

    @PrePersist
    public void prePersist() {
        if (this.rcommentId == null) {
            this.rcommentId = UUID.randomUUID().toString();
        }
        if (this.writeDate == null) {
            this.writeDate = LocalDateTime.now();
        }
    }
}
