package com.extrawest.ticker_app.controller;

import com.extrawest.ticker_app.dto.TickersActivePausedStatistic;
import com.extrawest.ticker_app.dto.TickersStartStopStatistic;
import com.extrawest.ticker_app.service.TicksHistoryService;
import java.security.Principal;
import java.util.Date;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticker_history")
@AllArgsConstructor
public class TicksHistoryController {
    private final TicksHistoryService ticksHistoryService;

    @GetMapping("/ticks/{id}")
    @PreAuthorize("hasAuthority('users:read')")
    public Integer getTickCountForPeriod(
            @PathVariable Integer id,
            @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy-HH:mm:ss") Date from,
            @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy-HH:mm:ss") Date to,
            Principal principal) {
        return ticksHistoryService.getTickCountForPeriod(id, from, to, principal.getName());
    }

    @GetMapping("/timeout/{id}")
    @PreAuthorize("hasAuthority('users:read')")
    public Double getAverageTickTimeout(@PathVariable Integer id, Principal principal) {
        return ticksHistoryService.getAverageTickTimeout(id, principal.getName());
    }

    @GetMapping("/lost/{id}")
    @PreAuthorize("hasAuthority('users:read')")
    public Integer getLostTicksCount(@PathVariable Integer id, Principal principal) {
        return ticksHistoryService.getLostTicksCount(id, principal.getName());
    }

    @GetMapping("/start_stop")
    @PreAuthorize("hasAuthority('users:read')")
    public TickersStartStopStatistic getStartStopTickersStatistic(Principal principal) {
        return ticksHistoryService.getStartStopTickersStatistic(principal.getName());
    }

    @GetMapping("/active_paused")
    @PreAuthorize("hasAuthority('users:read')")
    public TickersActivePausedStatistic getActivePausedTickersStatistic(Principal principal) {
        return ticksHistoryService.getActivePausedTickersStatistic(principal.getName());
    }
}
