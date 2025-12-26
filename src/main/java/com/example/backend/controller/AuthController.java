package com.example.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backend.dto.LoginRequestDto;
import com.example.backend.service.SejongAuthZService;
import com.example.backend.service.UserService;
import com.example.backend.service.CrawlingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final SejongAuthZService sejongAuthZService;
    private final UserService userService;
    private final CrawlingService crawlingService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto request) {

        // 1. 세종대 포털 로그인 (실패 시 여기서 예외)
        sejongAuthZService.login(request.id(), request.password());

        // 2. 우리 서비스 사용자 등록
        userService.register(request.id());

        crawlingService.requestCrawling(
                request.id(),
                request.password()
        );

        // 3. 성공 응답
        return ResponseEntity.ok("LOGIN_SUCCESS");
    }
}

