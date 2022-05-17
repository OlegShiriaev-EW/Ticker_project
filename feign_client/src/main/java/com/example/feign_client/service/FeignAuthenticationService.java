package com.example.feign_client.service;

import com.example.feign_client.dto.AuthenticationRequestDto;
import com.example.feign_client.dto.AuthenticationResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "feignAuth", url = "http://localhost:8080", name = "feignAuth")
public interface FeignAuthenticationService {
    @PostMapping("/login")
    AuthenticationResponseDto authenticate(@RequestBody AuthenticationRequestDto requestDto);
}
