package com.mycompany.mockjson.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationResponse {
    @JsonProperty("access_token")
    private String accessToken;

    // @JsonProperty("refresh_token")
    // private String refreshToken;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
        // this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
