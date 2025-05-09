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
@Table(name = "challenge_participants")
public class ChallengeParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long challengeId;

    private Long writerId;

    private String content;

    private String videoUrl;

    @Temporal(TemporalType.TIMESTAMP)
    private Date writeDate;
}
