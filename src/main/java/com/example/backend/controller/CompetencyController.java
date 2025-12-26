package com.example.backend.controller;

import com.example.backend.dto.CompetencyAnalysisRequestDto;
import com.example.backend.dto.CompetencyAnalysisResponseDto;
import com.example.backend.service.CompetencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/competency")
public class CompetencyController {

    private final CompetencyService competencyService;

    @PostMapping("/analyze")
    public ResponseEntity<CompetencyAnalysisResponseDto> analyze(
            @RequestBody CompetencyAnalysisRequestDto request
    ) {
        System.out.println("=== 받은 요청 ===");
        System.out.println("userId: " + request.userId());
        System.out.println("title: " + request.title());

        CompetencyAnalysisResponseDto response = competencyService.analyze(request.userId(), request.title());
        return ResponseEntity.ok(response);
    }
}