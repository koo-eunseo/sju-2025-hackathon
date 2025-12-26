package com.example.backend.repository;

import com.example.backend.entity.EnrollmentEntity;
import com.example.backend.entity.CourseEntity;
import com.example.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository
        extends JpaRepository<EnrollmentEntity, Long> {

    // ✅ 중복 체크 (이미 있음)
    boolean existsByUserAndCourse(
            UserEntity user,
            CourseEntity course
    );

    // 🔥 조회용 (이거 추가!)
    List<EnrollmentEntity> findByUserStudentId(String studentId);
}
