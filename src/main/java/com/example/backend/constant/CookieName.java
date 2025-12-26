package com.example.backend.constant;

public enum CookieName {
    PO_JSESSION("PO_JSESSIONID"),
    JSESSION("JSESSIONID"),
    SSO_TOKEN("ssotoken");

    private final String value;

    CookieName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
