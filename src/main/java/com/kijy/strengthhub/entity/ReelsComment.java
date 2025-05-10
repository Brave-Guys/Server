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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rcommentId;

    private String reelsId;

    private Long writerId;

    private Long parentId; // String → Long으로 수정

    @Column(columnDefinition = "TEXT")
    private String content;

    private int likes;

    private LocalDateTime writeDate;

    @PrePersist
    public void prePersist() {
        if (this.writeDate == null) {
            this.writeDate = LocalDateTime.now();
        }
    }
}
