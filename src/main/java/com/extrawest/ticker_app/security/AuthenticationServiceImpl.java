package com.extrawest.ticker_app.security;

import com.extrawest.ticker_app.dto.AuthenticationResponseDto;
import com.extrawest.ticker_app.exception.ApiRequestException;
import com.extrawest.ticker_app.model.Role;
import com.extrawest.ticker_app.model.User;
import com.extrawest.ticker_app.security.jwt.JwtTokenProvider;
import com.extrawest.ticker_app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public User register(String email, String password) {
        if (userService.findByEmail(email).isPresent()) {
            throw new ApiRequestException("User with email " + email + " already exist");
        }
        return userService.save(new User(email, password, Role.USER));
    }

    @Override
    public AuthenticationResponseDto login(String login, String password) throws AuthenticationException {
        User user = userService.getExistUser(login);
        String encodedPassword = passwordEncoder.encode(password);
        if (user.getPassword().equals(encodedPassword)) {
            throw new ApiRequestException("Incorrect username or password!!!");
        }
        return new AuthenticationResponseDto(jwtTokenProvider.createToken(user.getEmail(), user.getRole().name()));
    }
}
