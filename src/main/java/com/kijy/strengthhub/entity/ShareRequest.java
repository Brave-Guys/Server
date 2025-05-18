package com.kijy.strengthhub.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShareRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long masterId;
    private Long userId;
    private String age;
    private String height;
    private String weight;
    private String gender;

    @Column(length = 1000)
    private String content;
}
