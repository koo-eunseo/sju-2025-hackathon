package com.example.backend.dto;

import java.util.List;

public record CompetencyAnalysisResponseDto(
        List<CompetencyDto> competencies,
        String description,
        boolean isCustom
) {}