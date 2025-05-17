package com.kijy.strengthhub.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MasterRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String career;
    private String intro;
    private String link;

    @Column(length = 1000)
    private String parts;

    @Column(length = 2000)
    private String certFileUrls;

    @Column(length = 2000)
    private String portfolioUrls;
}
