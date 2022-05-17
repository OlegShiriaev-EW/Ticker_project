package com.example.feign_client.dto;

import lombok.Getter;

@Getter
public class AuthenticationRequestDto {
    private String email;
    private String password;

    public AuthenticationRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
