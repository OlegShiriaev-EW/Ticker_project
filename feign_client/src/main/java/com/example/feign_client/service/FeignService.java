package com.example.feign_client.service;

import com.example.feign_client.config.FeignConfig;
import com.example.feign_client.dto.TickerRequestDto;
import com.example.feign_client.dto.TickerResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "feignTickers", url = "http://localhost:8080", configuration = {FeignConfig.class})
public interface FeignService {
    @PostMapping("tickers")
    TickerResponseDto save(@RequestBody TickerRequestDto ticker);

    @GetMapping("/tickers/start/{id}")
    void start(@PathVariable Integer id);

    @GetMapping("/tickers/stop/{id}")
    void stop(@PathVariable Integer id);

    @PutMapping("/tickers/update/{id}")
    void update(@PathVariable Integer id, TickerRequestDto requestDto);
}
