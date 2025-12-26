package com.example.backend.util;

import org.springframework.http.HttpHeaders;

import java.util.*;

public class HttpUtil {

    public static Map<String, String> parseCookies(HttpHeaders headers) {
        Map<String, String> cookies = new HashMap<>();
        List<String> setCookies = headers.get(HttpHeaders.SET_COOKIE);

        if (setCookies == null) return cookies;

        for (String header : setCookies) {
            String[] parts = header.split(";");
            if (parts.length > 0) {
                String[] kv = parts[0].trim().split("=", 2);
                if (kv.length == 2) {
                    cookies.put(kv[0], kv[1]);
                }
            }
        }
        return cookies;
    }

    public static String toCookieHeader(Map<String, String> cookies) {
        return cookies.entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .reduce((a, b) -> a + "; " + b)
                .orElse("");
    }
}
