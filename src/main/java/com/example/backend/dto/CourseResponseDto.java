package com.example.backend.dto;
public record CourseResponseDto(
        String curiNo,
        String curiNm,
        String typeName,
        float cdt,

        // ✅ 추가
        String dept_m_alias,  // 학과
        String year,          // 년도
        String smt_cd   // 학기
) {}
