package com.example.backend.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
public record CompetencyAnalysisRequestDto(
        @JsonProperty("userId")
        String userId,
        String title
) {}