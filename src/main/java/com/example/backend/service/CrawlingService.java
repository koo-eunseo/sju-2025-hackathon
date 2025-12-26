package com.example.backend.service;

import com.example.backend.dto.CrawledCourseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrawlingService {

    // 👉 Python 크롤링 서버 주소
    private static final String PYTHON_CRAWLER_URL =
            "http://52.78.7.66:8000/crawl";

    private final EnrollmentService enrollmentService;

    @Async
    public void requestCrawling(String studentId, String password) {

        try {
            RestTemplate restTemplate = new RestTemplate();

            // ======================
            // 요청 헤더
            // ======================
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // ======================
            // 요청 바디
            // ======================
            Map<String, String> body = Map.of(
                    "user_id", studentId,
                    "password", password
            );

            HttpEntity<Map<String, String>> request =
                    new HttpEntity<>(body, headers);

            // ======================
            // Python 서버 호출
            // ======================
            ResponseEntity<CrawledCourseDto[]> response =
                    restTemplate.postForEntity(
                            PYTHON_CRAWLER_URL,
                            request,
                            CrawledCourseDto[].class
                    );
            // 이거 추가해서 서버 재시작 후 로그인 해보세요


            CrawledCourseDto[] courses = response.getBody();
            if (courses != null && courses.length > 0) {
                log.info("=== 크롤링 데이터 확인 ===");
                log.info("curi_no: [{}]", courses[0].curi_no());
                log.info("curi_nm: [{}]", courses[0].curi_nm());
                log.info("dept_m_alias: [{}]", courses[0].dept_m_alias());
                log.info("year: [{}]", courses[0].year());
                log.info("smt_cd: [{}]", courses[0].smt_cd());
            }

            if (courses == null || courses.length == 0) {
                log.warn("No courses crawled. studentId={}", studentId);
                return;
            }

            // ======================
            // DB 저장
            // ======================
            List<CrawledCourseDto> courseList = Arrays.asList(courses);

            enrollmentService.saveEnrollments(
                    studentId,
                    courseList
            );

            log.info("Crawling & save complete. studentId={}, count={}",
                    studentId, courseList.size());

        } catch (Exception e) {
            log.error("Crawling failed. studentId={}", studentId, e);
        }
    }
}
