package com.extrawest.ticker_app.service.scheduling;

import com.extrawest.ticker_app.dto.mapper.TicksHistoryMapper;
import com.extrawest.ticker_app.model.Side;
import com.extrawest.ticker_app.model.Status;
import com.extrawest.ticker_app.model.Ticker;
import com.extrawest.ticker_app.model.TicksHistory;
import com.extrawest.ticker_app.repository.TicksHistoryRepository;
import com.extrawest.ticker_app.service.KafkaService;
import com.extrawest.ticker_app.service.impl.TickerServiceImpl;
import java.util.Date;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@AllArgsConstructor
public class TickScheduler {
    private final KafkaService kafkaService;
    private final TicksHistoryMapper ticksHistoryMapper;
    private final TicksHistoryRepository ticksHistoryRepository;

    public Runnable doTask(Ticker ticker) {
        return () -> {
            TicksHistory ticksHistory = ticksHistoryRepository.findFirstByTickerOrderByTimestampDesc(ticker)
                    .orElse(new TicksHistory(new Date(), Side.LEFT, ticker.getInterval(), ticker));
            if (Status.ACTIVE == ticksHistory.getTicker().getStatus()
                    && ticker.getInterval() == ticksHistory.getTicker().getInterval()) {
                kafkaService.send(ticksHistoryMapper.toDto(new TicksHistory(new Date(),
                        ticksHistory.getSide() == Side.RIGHT ? Side.LEFT : Side.RIGHT,
                        (double) (new Date().getTime() - ticksHistory.getTimestamp().getTime())
                                / TickerServiceImpl.ONE_SECOND,
                        ticker)));
            }
        };
    }
}