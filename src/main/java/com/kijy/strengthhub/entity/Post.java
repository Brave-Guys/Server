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
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String writerId;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String category;

    private String imageUrls;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    private int likeCount;

    private int commentCount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
