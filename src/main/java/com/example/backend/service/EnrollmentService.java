package com.example.backend.service;

import com.example.backend.dto.CrawledCourseDto;
import com.example.backend.dto.CourseResponseDto;
import com.example.backend.entity.CourseEntity;
import com.example.backend.entity.EnrollmentEntity;
import com.example.backend.entity.UserEntity;
import com.example.backend.repository.CourseRepository;
import com.example.backend.repository.EnrollmentRepository;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    /**
     * 🔹 크롤링 결과 저장
     */
    @Transactional
    public void saveEnrollments(
            String studentId,
            List<CrawledCourseDto> courses
    ) {
        UserEntity user = userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        for (CrawledCourseDto dto : courses) {

            CourseEntity course = courseRepository
                    .findByCuriNo(dto.curi_no())
                    .orElseGet(() -> courseRepository.save(
                            CourseEntity.builder()
                                    .curiNo(dto.curi_no())
                                    .curiNm(dto.curi_nm())
                                    .typeName(dto.type_name())
                                    .cdt(dto.cdt())
                                    .build()
                    ));

            boolean exists = enrollmentRepository
                    .existsByUserAndCourse(user, course);

            if (exists) continue;

            enrollmentRepository.save(
                    EnrollmentEntity.builder()
                            .user(user)
                            .course(course)
                            .deptMNm(dto.dept_m_alias())   // ✅ 학과
                            .year(dto.year())              // ✅ 년도
                            .semester(dto.smt_cd())
                            .build()
            );
        }
    }

    /**
     * 🔹 수강 과목 조회 (이게 새로 추가된 부분!)
     */
    @Transactional(readOnly = true)
    public List<CourseResponseDto> getCoursesByStudentId(String studentId) {

        return enrollmentRepository.findByUserStudentId(studentId)
                .stream()
                .map(e -> new CourseResponseDto(
                        e.getCourse().getCuriNo(),
                        e.getCourse().getCuriNm(),
                        e.getCourse().getTypeName(),
                        e.getCourse().getCdt(),

                        // 🔥 Enrollment 쪽 정보
                        e.getDeptMNm(),    // 학과
                        e.getYear(),       // 년도
                        e.getSemester()    // 학기
                ))
                .toList();
    }
}
