package com.extrawest.ticker_app.dto.mapper;

import com.extrawest.ticker_app.dto.TicksHistoryDto;
import com.extrawest.ticker_app.model.TicksHistory;
import com.extrawest.ticker_app.repository.TickerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TicksHistoryMapper {
    private final TickerRepository tickerRepository;

    public TicksHistoryDto toDto(TicksHistory ticksHistory) {
        TicksHistoryDto ticksHistoryDto = new TicksHistoryDto();
        ticksHistoryDto.setTimestamp(ticksHistory.getTimestamp());
        ticksHistoryDto.setSide(ticksHistory.getSide());
        ticksHistoryDto.setCurrentInterval(ticksHistory.getCurrentInterval());
        ticksHistoryDto.setTicker(ticksHistory.getTicker().getId());
        return ticksHistoryDto;
    }

    public TicksHistory toModel(TicksHistoryDto dto) {
        TicksHistory ticksHistory = new TicksHistory();
        ticksHistory.setTimestamp(dto.getTimestamp());
        ticksHistory.setSide(dto.getSide());
        ticksHistory.setCurrentInterval(dto.getCurrentInterval());
        ticksHistory.setTicker(tickerRepository.findById(dto.getTicker()).get());
        return ticksHistory;
    }
}
