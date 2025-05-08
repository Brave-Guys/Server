package com.kijy.strengthhub.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users") // user는 예약어일 수 있어서 users로
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, name = "user_id")
    private String userId; // username

    private String password;

    private String name; // nickname

    private String email;

    private String role; // USER or ADMIN

    private String userPlanType; // BEGINNER, ...
}
