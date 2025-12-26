package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 교과목 코드
    @Column(name = "curi_no", nullable = false, unique = true)
    private String curiNo;

    // 교과목명
    private String curiNm;

    // 이수구분
    private String typeName;

    // 학점
    private float cdt;

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

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EnrollmentEntity> enrollments = new ArrayList<>();
}
