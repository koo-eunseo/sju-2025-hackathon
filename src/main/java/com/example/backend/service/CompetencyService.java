package com.example.backend.service;

import com.example.backend.dto.AiAnalysisRequestDto;
import com.example.backend.dto.CompetencyAnalysisResponseDto;
import com.example.backend.dto.CompetencyDto;
import com.example.backend.entity.CareerAnalysisEntity;
import com.example.backend.entity.CompetencyResultEntity;
import com.example.backend.repository.CareerAnalysisRepository;
import com.example.backend.repository.CompetencyResultRepository;
import com.example.backend.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompetencyService {

    private static final String AI_SERVER_URL = "https://roadmap-ai-yqbm.onrender.com/api/analysis/with-courses";

    private final CareerAnalysisRepository careerAnalysisRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CompetencyResultRepository competencyResultRepository;

    public CompetencyAnalysisResponseDto analyze(String userId, String title) {

        // 1. subject 리스트 조회 (career_analysis 테이블에서)
        List<String> subjectList = careerAnalysisRepository.findByTitle(title)
                .stream()
                .map(CareerAnalysisEntity::getSubject)
                .toList();

        log.info("=== subject 리스트 ===");
        log.info("title: {}", title);
        log.info("subjectList: {}", subjectList);
        log.info("subjectList 개수: {}", subjectList.size());
        // 2. 기이수과목 리스트 조회 (enrollments에서 userId로)
        List<String> completedCourses = enrollmentRepository.findByUserStudentId(userId)
                .stream()
                .map(e -> e.getCourse().getCuriNm())
                .toList();

        log.info("=== 기이수과목 리스트 ===");
        log.info("userId: {}", userId);
        log.info("completedCourses: {}", completedCourses);
        log.info("completedCourses 개수: {}", completedCourses.size());

        // 3. AI 서버 호출
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        AiAnalysisRequestDto body = new AiAnalysisRequestDto(title, subjectList, completedCourses);
        HttpEntity<AiAnalysisRequestDto> request = new HttpEntity<>(body, headers);

        ResponseEntity<CompetencyAnalysisResponseDto> response = restTemplate.postForEntity(
                AI_SERVER_URL,
                request,
                CompetencyAnalysisResponseDto.class
        );

        CompetencyAnalysisResponseDto result = response.getBody();

        // 4. DB에 저장
        // 4. DB에 저장
        if (result != null && result.competencies() != null) {
            for (CompetencyDto comp : result.competencies()) {
                competencyResultRepository.save(
                        CompetencyResultEntity.builder()
                                .userId(userId)
                                .title(title)
                                .type("analysis")
                                .subject(comp.subject())
                                .score(comp.score())
                                .fullMark(comp.fullMark())
                                .build()
                );
            }
            log.info("Competency analysis saved. userId={}, title={}", userId, title);
        }

        return result;
    }
}