package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "career_analysis")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CareerAnalysisEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String subject;

    private int score;

    private int fullMark;

    @Builder
    public CareerAnalysisEntity(String title, String subject, int score, int fullMark) {
        this.title = title;
        this.subject = subject;
        this.score = score;
        this.fullMark = fullMark;
    }
}