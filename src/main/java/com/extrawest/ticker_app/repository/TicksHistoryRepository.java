package com.extrawest.ticker_app.repository;

import com.extrawest.ticker_app.model.Ticker;
import com.extrawest.ticker_app.model.TicksHistory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicksHistoryRepository extends MongoRepository<TicksHistory, String> {
    Optional<TicksHistory> findFirstByTickerOrderByTimestampDesc(Ticker ticker);

    Optional<List<TicksHistory>> findAllByTicker(Ticker ticker);
}
