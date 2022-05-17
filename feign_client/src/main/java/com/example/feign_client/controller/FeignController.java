package com.example.feign_client.controller;

import com.example.feign_client.dto.TickerRequestDto;
import com.example.feign_client.dto.TickerResponseDto;
import com.example.feign_client.dto.UserRequestDto;
import com.example.feign_client.dto.UserResponseDto;
import com.example.feign_client.service.FeignRegisterService;
import com.example.feign_client.service.FeignService;
import java.util.Random;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feign")
@AllArgsConstructor
public class FeignController {
    private final FeignService feignService;
    private final FeignRegisterService registerService;
    private static final Integer TICKS_RANDOM_SECONDS_INTERVAL = 9;
    private static final Integer TICKS_RANDOM_ID = 7;

    @PostMapping("/register")
    public UserResponseDto register() {
        return registerService.register(new UserRequestDto("xaxaxa@gmail.com",
                "xaxaxa",
                "xaxaxa"));
    }

    @PostMapping("tickers")
    TickerResponseDto save() {
        return feignService
                .save(new TickerRequestDto(new Random().nextInt(TICKS_RANDOM_SECONDS_INTERVAL) + 1));
    }

    @GetMapping("/tickers/start")
    void start() {
        feignService.start(new Random().nextInt(TICKS_RANDOM_ID) + 1);
    }

    @GetMapping("/tickers/stop")
    void stop() {
        feignService.stop(new Random().nextInt(TICKS_RANDOM_ID) + 1);
    }

    @GetMapping("/tickers/update")
    void update() {
        feignService.update(new Random().nextInt(7) + 1,
                new TickerRequestDto(new Random().nextInt(TICKS_RANDOM_SECONDS_INTERVAL) + 1));
    }
}
