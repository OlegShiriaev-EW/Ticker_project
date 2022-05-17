package com.extrawest.ticker_app.service;

import com.extrawest.ticker_app.dto.TicksHistoryDto;

public interface KafkaService {
    void send(TicksHistoryDto dto);

    void consume(TicksHistoryDto dto);
}
