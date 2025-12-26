package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "competency_results")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompetencyResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String title;

    private String type;  // "current" or "target"

    private String subject;

    private int score;

    private int fullMark;

    @Builder
    public CompetencyResultEntity(String userId, String title, String type, String subject, int score, int fullMark) {
        this.userId = userId;
        this.title = title;
        this.type = type;
        this.subject = subject;
        this.score = score;
        this.fullMark = fullMark;
    }
}