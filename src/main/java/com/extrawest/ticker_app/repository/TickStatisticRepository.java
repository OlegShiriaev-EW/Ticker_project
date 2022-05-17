package com.extrawest.ticker_app.repository;

import com.extrawest.ticker_app.model.Status;
import com.extrawest.ticker_app.model.TickStatistic;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TickStatisticRepository extends MongoRepository<TickStatistic, String> {
    List<TickStatistic> findAllByUserIdAndStatus(Integer userId, Status status);
}
