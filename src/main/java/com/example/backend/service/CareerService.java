package com.example.backend.service;

import com.example.backend.dto.CareerResponseDto;
import com.example.backend.dto.CompetencyDto;
import com.example.backend.entity.CareerAnalysisEntity;
import com.example.backend.repository.CareerAnalysisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CareerService {

    private static final String AI_SERVER_URL =
            "https://roadmap-ai-yqbm.onrender.com/api/analysis";

    private final CareerAnalysisRepository careerAnalysisRepository;

    public CareerResponseDto analyzeCareer(String title) {

        log.info("=== CareerService 호출 ===");
        log.info("받은 title: [{}]", title);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = Map.of("title", title);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<CareerResponseDto> response =
                restTemplate.postForEntity(
                        AI_SERVER_URL,
                        request,
                        CareerResponseDto.class
                );

        CareerResponseDto result = response.getBody();

        if (result == null || result.competencies() == null) {
            log.warn("AI 응답이 비어 있음");
            return result;
        }

        // ✅ 1. 기존 데이터 삭제 (딱 한 번!)
        careerAnalysisRepository.deleteByTitle(title);

        // ✅ 2. 새 데이터 저장
        for (CompetencyDto comp : result.competencies()) {
            CareerAnalysisEntity entity = CareerAnalysisEntity.builder()
                    .title(title)
                    .subject(comp.subject())
                    .score(comp.score())
                    .fullMark(comp.fullMark())
                    .build();

            careerAnalysisRepository.save(entity);
        }

        log.info("Career analysis saved. title={}, count={}",
                title, result.competencies().size());

        return result;
    }
}
