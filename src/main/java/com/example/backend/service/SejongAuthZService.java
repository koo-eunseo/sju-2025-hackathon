package com.example.backend.service;

import com.example.backend.constant.AuthEndpoint;
import com.example.backend.constant.CookieName;
import com.example.backend.dto.AuthTokenDto;
import com.example.backend.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class SejongAuthZService {

    private static final Pattern LOGIN_RESULT_REGEX =
            Pattern.compile("var\\s+result\\s*=\\s*['\"]([^'\"]+)['\"]");

    private static final String SUCCESS = "OK";

    /**
     * 세종대 포털 로그인
     */
    public AuthTokenDto login(String username, String password) {
        ResponseEntity<String> response = requestLogin(username, password);

        if (!checkLoginStatus(response.getBody())) {
            throw new RuntimeException("SEJONG_PORTAL_LOGIN_FAILED");
        }

        Map<String, String> cookies = HttpUtil.parseCookies(response.getHeaders());

        String jsessionId = cookies.getOrDefault(
                CookieName.PO_JSESSION.getValue(),
                cookies.getOrDefault(CookieName.JSESSION.getValue(), "")
        );

        String ssoToken = cookies.getOrDefault(
                CookieName.SSO_TOKEN.getValue(),
                ""
        );

        return AuthTokenDto.of(jsessionId, ssoToken);
    }

    /**
     * 실제 로그인 POST 요청
     */
    private ResponseEntity<String> requestLogin(String username, String password) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("id", username);
        form.add("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Host", "portal.sejong.ac.kr");
        headers.set("Referer", "https://portal.sejong.ac.kr/jsp/login/loginSSL.jsp");
        headers.set("User-Agent", "Mozilla/5.0");

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(form, headers);

        return restTemplate.exchange(
                AuthEndpoint.LOGIN.url(),
                HttpMethod.POST,
                request,
                String.class
        );
    }

    /**
     * 로그인 성공 여부 확인
     */
    private boolean checkLoginStatus(String body) {
        Matcher matcher = LOGIN_RESULT_REGEX.matcher(body);
        if (!matcher.find()) {
            return false;
        }
        return SUCCESS.equals(matcher.group(1));
    }
}
