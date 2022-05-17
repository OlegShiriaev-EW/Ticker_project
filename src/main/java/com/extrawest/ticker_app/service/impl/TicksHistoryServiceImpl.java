package com.extrawest.ticker_app.service.impl;

import com.extrawest.ticker_app.dto.TickersActivePausedStatistic;
import com.extrawest.ticker_app.dto.TickersStartStopStatistic;
import com.extrawest.ticker_app.exception.ApiRequestException;
import com.extrawest.ticker_app.model.Status;
import com.extrawest.ticker_app.model.TickStatistic;
import com.extrawest.ticker_app.model.Ticker;
import com.extrawest.ticker_app.model.TicksHistory;
import com.extrawest.ticker_app.repository.TickStatisticRepository;
import com.extrawest.ticker_app.repository.TicksHistoryRepository;
import com.extrawest.ticker_app.service.TickerService;
import com.extrawest.ticker_app.service.TicksHistoryService;
import com.extrawest.ticker_app.service.UserService;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TicksHistoryServiceImpl implements TicksHistoryService {
    private final TicksHistoryRepository ticksHistoryRepository;
    private final UserService userService;
    private final TickStatisticRepository tickStatisticRepository;
    private final TickerService tickerService;

    @Override
    public void save(TicksHistory ticksHistory) {
        ticksHistoryRepository.save(ticksHistory);
    }

    @Override
    public void save(TickStatistic tickStatistic) {
        tickStatisticRepository.save(tickStatistic);
    }

    @Override
    public Integer getTickCountForPeriod(Integer tickerId, Date from, Date to, String userName) {
        var ticksHistoryList = getTicksListByTickerIdAndUserName(tickerId, userName);
        return Math.toIntExact(ticksHistoryList.stream()
                .filter(time -> time.getTimestamp().after(from)
                        && time.getTimestamp().before(to))
                .count());
    }

    @Override
    public Double getAverageTickTimeout(Integer tickerId, String userName) {
        var ticksHistoryList = getTicksListByTickerIdAndUserName(tickerId, userName);
        return ticksHistoryList.stream()
                .mapToDouble(TicksHistory::getCurrentInterval)
                .sum() / (ticksHistoryList.size() - 1);
    }

    @Override
    public Integer getLostTicksCount(Integer tickerId, String userName) {
        var ticksHistoryList = getTicksListByTickerIdAndUserName(tickerId, userName);
        int count = 0;
        for (int i = 1; i < ticksHistoryList.size(); i++) {
            if (ticksHistoryList.get(i - 1).getSide() == ticksHistoryList.get(i).getSide()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public List<TicksHistory> getAllByTicker(Ticker ticker) {
        return ticksHistoryRepository.findAllByTicker(ticker).orElseThrow(() ->
                new ApiRequestException("You have no ticker with id " + ticker.getId()));
    }

    @Override
    public TickersStartStopStatistic getStartStopTickersStatistic(String userName) {
        Integer userId = userService.getExistUser(userName).getId();
        return new TickersStartStopStatistic(
                tickStatisticRepository.findAllByUserIdAndStatus(userId, Status.ACTIVE).size(),
                tickStatisticRepository.findAllByUserIdAndStatus(userId, Status.PAUSED).size());
    }

    @Override
    public TickersActivePausedStatistic getActivePausedTickersStatistic(String userName) {
        List<Ticker> tickersList = tickerService.findAllByUser(userName);
        long activeCount = tickersList.stream()
                .filter(tick -> tick.getStatus() == Status.ACTIVE)
                .count();
        long pausedCount = tickersList.size() - activeCount;
        return new TickersActivePausedStatistic(activeCount, pausedCount);
    }

    public List<TicksHistory> getTicksListByTickerIdAndUserName(Integer tickerId, String userName) {
        Ticker ticker = tickerService.findByIdAndUser(tickerId, userName);
        return getAllByTicker(ticker);
    }
}
