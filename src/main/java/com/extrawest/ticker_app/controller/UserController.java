package com.extrawest.ticker_app.controller;

import com.extrawest.ticker_app.dto.UserRequestDto;
import com.extrawest.ticker_app.dto.UserResponseDto;
import com.extrawest.ticker_app.dto.mapper.UserMapper;
import com.extrawest.ticker_app.security.AuthenticationService;
import com.extrawest.ticker_app.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('users:write')")
    public UserResponseDto save(@RequestBody @Valid UserRequestDto userRequestDto) {
        return userMapper.toDto(authenticationService
                .register(userRequestDto.getEmail(), userRequestDto.getPassword()));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('users:read')")
    public List<UserResponseDto> getAll() {
        return userService.getAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
}
