package com.extrawest.ticker_app.dto.mapper;

import com.extrawest.ticker_app.dto.TickerRequestDto;
import com.extrawest.ticker_app.dto.TickerResponseDto;
import com.extrawest.ticker_app.model.Ticker;
import org.springframework.stereotype.Component;

@Component
public class TickerMapper {
    public Ticker toModel(TickerRequestDto requestDto) {
        Ticker ticker = new Ticker();
        ticker.setInterval(requestDto.getInterval());
        return ticker;
    }

    public TickerResponseDto toDto(Ticker ticker) {
        TickerResponseDto responseDto = new TickerResponseDto();
        responseDto.setId(ticker.getId());
        responseDto.setInterval(ticker.getInterval());
        responseDto.setStatus(ticker.getStatus());
        return responseDto;
    }
}
