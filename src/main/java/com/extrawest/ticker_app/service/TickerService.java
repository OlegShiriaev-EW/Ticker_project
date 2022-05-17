package com.extrawest.ticker_app.service;

import com.extrawest.ticker_app.dto.TickerRequestDto;
import com.extrawest.ticker_app.model.Status;
import com.extrawest.ticker_app.model.Ticker;
import java.util.List;

public interface TickerService {
    Ticker save(Ticker ticker, String userName);

    Ticker findByIdAndUser(Integer tickerId, String userName);

    void start(Integer tickerId, String userName);

    void stop(Integer tickerId, String userName);

    void updateStatus(Ticker ticker, Status status);

    void updateInterval(Integer tickerId, TickerRequestDto requestDto, String userName);

    List<Ticker> findAllByUser(String userName);
}
