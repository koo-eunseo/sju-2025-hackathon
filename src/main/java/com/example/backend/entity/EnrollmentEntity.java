package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "enrollments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EnrollmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ======================
    // N : 1 User
    // ======================
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    // ======================
    // N : 1 Course
    // ======================
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity course;

    // ======================
    // 학과 (크롤링 데이터)
    // ======================
    @Column(name = "dept_m_alias")
    private String deptMNm;

    // ======================
    // 수강 연도
    // ======================
    private String year;

    // ======================
    // 수강 학기 (ex. 1학기, 2학기)
    // ======================
    private String semester;

    // ======================
    // Builder
    // ======================
    @Builder
    public EnrollmentEntity(
            UserEntity user,
            CourseEntity course,
            String deptMNm,
            String year,
            String semester
    ) {
        this.user = user;
        this.course = course;
        this.deptMNm = deptMNm;
        this.year = year;
        this.semester = semester;
    }
}
