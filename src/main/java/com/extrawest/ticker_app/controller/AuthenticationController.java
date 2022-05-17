package com.extrawest.ticker_app.controller;

import com.extrawest.ticker_app.dto.AuthenticationRequestDto;
import com.extrawest.ticker_app.dto.AuthenticationResponseDto;
import com.extrawest.ticker_app.dto.UserRequestDto;
import com.extrawest.ticker_app.dto.UserResponseDto;
import com.extrawest.ticker_app.dto.mapper.UserMapper;
import com.extrawest.ticker_app.model.User;
import com.extrawest.ticker_app.security.AuthenticationService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody @Valid UserRequestDto requestDto) {
        User user = authenticationService.register(requestDto.getEmail(), requestDto.getPassword());
        return userMapper.toDto(user);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> authenticate(
            @RequestBody @Valid AuthenticationRequestDto requestDto) {
        return ResponseEntity.ok(authenticationService.login(requestDto.getEmail(),
                requestDto.getPassword()));
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
