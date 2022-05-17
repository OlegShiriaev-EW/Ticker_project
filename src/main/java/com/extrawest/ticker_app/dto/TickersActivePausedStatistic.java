package com.extrawest.ticker_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TickersActivePausedStatistic {
    private long activeTickersStatistic;
    private long pausedTickersStatistic;
}
