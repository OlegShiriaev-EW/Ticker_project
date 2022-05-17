package com.extrawest.ticker_app.service;

import com.extrawest.ticker_app.dto.TickersActivePausedStatistic;
import com.extrawest.ticker_app.dto.TickersStartStopStatistic;
import com.extrawest.ticker_app.model.TickStatistic;
import com.extrawest.ticker_app.model.Ticker;
import com.extrawest.ticker_app.model.TicksHistory;
import java.util.Date;
import java.util.List;

public interface TicksHistoryService {
    void save(TicksHistory ticksHistory);

    void save(TickStatistic tickStatistic);

    Integer getTickCountForPeriod(Integer tickerId, Date from, Date to, String userName);

    Double getAverageTickTimeout(Integer tickerId, String userName);

    Integer getLostTicksCount(Integer tickerId, String userName);

    List<TicksHistory> getAllByTicker(Ticker ticker);

    TickersStartStopStatistic getStartStopTickersStatistic(String userName);

    TickersActivePausedStatistic getActivePausedTickersStatistic(String userName);
}
