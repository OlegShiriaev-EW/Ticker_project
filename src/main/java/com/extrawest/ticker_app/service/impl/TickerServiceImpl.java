package com.extrawest.ticker_app.service.impl;

import com.extrawest.ticker_app.dto.TickerRequestDto;
import com.extrawest.ticker_app.exception.ApiRequestException;
import com.extrawest.ticker_app.model.Status;
import com.extrawest.ticker_app.model.TickStatistic;
import com.extrawest.ticker_app.model.Ticker;
import com.extrawest.ticker_app.repository.TickStatisticRepository;
import com.extrawest.ticker_app.repository.TickerRepository;
import com.extrawest.ticker_app.service.TickerService;
import com.extrawest.ticker_app.service.UserService;
import com.extrawest.ticker_app.service.scheduling.TickScheduler;
import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@AllArgsConstructor
public class TickerServiceImpl implements TickerService {
    public final static Long ONE_SECOND = 1000L;
    private final TickerRepository tickerRepository;
    private final TickerSequenceGeneratorService generatorService;
    private final UserService userService;
    private final TickScheduler tickScheduler;
    private final ThreadPoolTaskScheduler taskScheduler;
    private final TickStatisticRepository tickStatisticRepository;

    @Override
    public Ticker save(Ticker ticker, String userName) {
        ticker.setId(generatorService.getSequenceNumber(Ticker.SEQUENCE_NAME));
        ticker.setUser(userService.getExistUser(userName));
        ticker.setStatus(Status.NEW);
        saveTickStatistic(ticker);
        return tickerRepository.save(ticker);
    }

    @Override
    public Ticker findByIdAndUser(Integer id, String userName) {
        return tickerRepository.findByIdAndUser(id, userService.getExistUser(userName))
                .orElseThrow(() -> new ApiRequestException("You have no ticker with id " + id));
    }

    @Override
    public void start(Integer tickerId, String userName) {
        Ticker ticker = findByIdAndUser(tickerId, userName);
        if (Status.ACTIVE != ticker.getStatus()) {
            runTick(ticker);
            saveTickStatistic(ticker);
        }
    }

    @Override
    public void stop(Integer tickerId, String userName) {
        Ticker ticker = findByIdAndUser(tickerId, userName);
        if (Status.ACTIVE == ticker.getStatus()) {
            updateStatus(ticker, Status.PAUSED);
            saveTickStatistic(ticker);
        }
    }

    @Override
    public void updateStatus(Ticker ticker, Status status) {
        ticker.setStatus(status);
        tickerRepository.save(ticker);
    }

    @Override
    public void updateInterval(Integer tickerId, TickerRequestDto requestDto, String userName) {
        Ticker ticker = findByIdAndUser(tickerId, userName);
        if (Status.ACTIVE == ticker.getStatus()) {
            updateIntervalInUSe(ticker, requestDto.getInterval());
        }
            ticker.setInterval(requestDto.getInterval());
            tickerRepository.save(ticker);
    }

    @Override
    public List<Ticker> findAllByUser(String user) {
        return tickerRepository.findAllByUser(userService.getExistUser(user));
    }

    private void updateIntervalInUSe(Ticker ticker, Integer newInterval) {
        ticker.setInterval(newInterval);
        runTick(ticker);
    }

    private void runTick(Ticker ticker) {
        updateStatus(ticker, Status.ACTIVE);
        taskScheduler.scheduleWithFixedDelay(tickScheduler.doTask(ticker),
                ticker.getInterval() * ONE_SECOND);
    }

    private void saveTickStatistic(Ticker ticker) {
        TickStatistic tickStatistic = new TickStatistic(new Date(),
                ticker.getId(),
                ticker.getStatus(),
                ticker.getUser().getId());
        tickStatisticRepository.save(tickStatistic);
    }

    @PostConstruct
    private void init() {
        List<Ticker> activeTickersList = tickerRepository.findAllByStatus(Status.ACTIVE);
        for (Ticker tick : activeTickersList) {
            runTick(tick);
        }
    }
}
