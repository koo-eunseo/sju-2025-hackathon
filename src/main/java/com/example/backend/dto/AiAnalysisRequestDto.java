package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
public record AiAnalysisRequestDto(
        String title,
        @JsonProperty("subject_list")
        List<String> subjectList,

        @JsonProperty("completedCourses")
        List<String> completedCourses
) {}