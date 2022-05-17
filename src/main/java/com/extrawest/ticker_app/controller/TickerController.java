package com.extrawest.ticker_app.controller;

import com.extrawest.ticker_app.dto.TickerRequestDto;
import com.extrawest.ticker_app.dto.TickerResponseDto;
import com.extrawest.ticker_app.dto.mapper.TickerMapper;
import com.extrawest.ticker_app.model.Ticker;
import com.extrawest.ticker_app.service.TickerService;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickers")
@AllArgsConstructor
public class TickerController {
    private final TickerService tickerService;
    private final TickerMapper tickerMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('users:read')")
    public TickerResponseDto save(@RequestBody @Valid TickerRequestDto ticker, Principal principal) {
        return tickerMapper.toDto(tickerService.save(tickerMapper.toModel(ticker), principal.getName()));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('users:read')")
    public List<Ticker> getAll(Principal principal) {
        return tickerService.findAllByUser(principal.getName());
    }

    @GetMapping("/start/{id}")
    @PreAuthorize("hasAuthority('users:read')")
    public void start(@PathVariable Integer id, Principal principal) {
        tickerService.start(id, principal.getName());
    }

    @GetMapping("/stop/{id}")
    @PreAuthorize("hasAuthority('users:read')")
    public void stop(@PathVariable Integer id, Principal principal) {
        tickerService.stop(id, principal.getName());
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('users:read')")
    public void update(@PathVariable Integer id,
                       @RequestBody @Valid TickerRequestDto requestDto,
                       Principal principal) {
        tickerService.updateInterval(id, requestDto, principal.getName());
    }
}
