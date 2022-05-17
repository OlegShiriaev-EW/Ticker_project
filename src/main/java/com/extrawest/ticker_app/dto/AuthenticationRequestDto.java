package com.extrawest.ticker_app.dto;

import com.extrawest.ticker_app.validate.ValidEmail;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class AuthenticationRequestDto {
    @ValidEmail
    private String email;
    @Size(min = 6, max = 12)
    private String password;

    public AuthenticationRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
