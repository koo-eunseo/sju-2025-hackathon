package com.example.backend.dto;

import java.util.List;

public record CareerResponseDto(
        String title,
        List<CompetencyDto> competencies
) {}