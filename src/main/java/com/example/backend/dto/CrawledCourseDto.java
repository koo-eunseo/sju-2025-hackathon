package com.example.backend.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
public record CrawledCourseDto(
        @JsonProperty("curi_no") String curi_no,
        @JsonProperty("curi_nm") String curi_nm,
        @JsonProperty("cdt") float cdt,
        @JsonProperty("type_name") String type_name,
        @JsonProperty("dept_m_alias") String dept_m_alias,
        @JsonProperty("year") String year,
        @JsonProperty("smt_cd") String smt_cd
) {}
