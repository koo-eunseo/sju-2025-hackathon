package com.example.backend.controller;

import com.example.backend.dto.CareerRequestDto;
import com.example.backend.dto.CareerResponseDto;
import com.example.backend.service.CareerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CareerController {

    private final CareerService careerService;

    @PostMapping("/careers")
    public ResponseEntity<CareerResponseDto> analyzeCareer(
            @RequestBody CareerRequestDto request
    ) {
        CareerResponseDto response = careerService.analyzeCareer(request.title());
        return ResponseEntity.ok(response);
    }
}