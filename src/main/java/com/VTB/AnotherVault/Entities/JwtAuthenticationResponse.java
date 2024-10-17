package com.VTB.AnotherVault.Entities;

public class JwtAuthenticationResponse {

    private String accessToken;
    private String tokenType = "Bearer";  // Обычно указывают тип токена

    // Конструктор с параметрами
    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    // Геттер для accessToken
    public String getAccessToken() {
        return accessToken;
    }

    // Сеттер для accessToken (если нужно)
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    // Геттер для tokenType
    public String getTokenType() {
        return tokenType;
    }

    // Сеттер для tokenType (если нужно)
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
