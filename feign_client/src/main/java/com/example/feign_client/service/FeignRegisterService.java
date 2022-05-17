package com.example.feign_client.service;

import com.example.feign_client.dto.UserRequestDto;
import com.example.feign_client.dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "feignRegister", url = "http://localhost:8080", name = "feignRegister")
public interface FeignRegisterService {
    @PostMapping("/register")
    UserResponseDto register(@RequestBody UserRequestDto requestDto);
}
