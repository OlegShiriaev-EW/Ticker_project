package com.example.feign_client.config;

import com.example.feign_client.dto.AuthenticationRequestDto;
import com.example.feign_client.dto.AuthenticationResponseDto;
import com.example.feign_client.service.FeignAuthenticationService;
import com.example.feign_client.util.SpringContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.util.Optional;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

class AuthInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        var service = SpringContext.getBean(FeignAuthenticationService.class);
        var response = Optional.ofNullable(service
                .authenticate(new AuthenticationRequestDto("xaxaxa@gmail.com", "xaxaxa")))
                .map(AuthenticationResponseDto::getToken).orElseThrow(() ->
                        new AuthenticationCredentialsNotFoundException("Invalid credentials"));
        template.header("Authorization", response);
    }
}