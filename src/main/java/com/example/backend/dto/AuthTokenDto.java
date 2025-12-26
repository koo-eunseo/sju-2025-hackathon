package com.example.backend.dto;

import com.example.backend.constant.CookieName;

import java.util.Map;

public record AuthTokenDto(
        String jsessionId,
        String ssoToken
) {
    public static AuthTokenDto of(String jsessionId, String ssoToken) {
        return new AuthTokenDto(
                jsessionId != null ? jsessionId : "",
                ssoToken != null ? ssoToken : ""
        );
    }

    public Map<String, String> toMap() {
        return Map.of(
                CookieName.PO_JSESSION.getValue(), jsessionId,
                CookieName.SSO_TOKEN.getValue(), ssoToken
        );
    }
}
