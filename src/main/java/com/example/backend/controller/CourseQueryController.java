package com.example.backend.controller;

import com.example.backend.dto.CourseResponseDto;
import com.example.backend.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class CourseQueryController {

    private final EnrollmentService enrollmentService;

    /**
     *  특정 학생의 수강 과목 조회
     */
    @GetMapping("/{studentId}/courses")
    public ResponseEntity<List<CourseResponseDto>> getUserCourses(
            @PathVariable String studentId
    ) {
        List<CourseResponseDto> courses =
                enrollmentService.getCoursesByStudentId(studentId);

        return ResponseEntity.ok(courses);
    }
}
