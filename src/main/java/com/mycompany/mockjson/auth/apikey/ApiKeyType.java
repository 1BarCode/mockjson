package com.mycompany.mockjson.auth.apikey;

public enum ApiKeyType {
    READ_ONLY("read-only"),
    READ_WRITE("read-write");

    private final String value;

    private ApiKeyType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
