package com.extrawest.ticker_app.repository;

import com.extrawest.ticker_app.model.Status;
import com.extrawest.ticker_app.model.Ticker;
import com.extrawest.ticker_app.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TickerRepository extends MongoRepository<Ticker, Integer> {
    Optional<Ticker> findByIdAndUser(Integer id, User  user);

    List<Ticker> findAllByStatus(Status status);

    List<Ticker> findAllByUser(User user);
}
