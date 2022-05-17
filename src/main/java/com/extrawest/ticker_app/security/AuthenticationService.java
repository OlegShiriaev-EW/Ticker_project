package com.extrawest.ticker_app.security;

import com.extrawest.ticker_app.dto.AuthenticationResponseDto;
import com.extrawest.ticker_app.model.User;
import org.springframework.security.core.AuthenticationException;

public interface AuthenticationService {
    User register(String email, String password);

    AuthenticationResponseDto login(String login, String password) throws AuthenticationException;
}
