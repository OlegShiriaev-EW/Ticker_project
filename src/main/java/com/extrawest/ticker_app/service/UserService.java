package com.extrawest.ticker_app.service;

import com.extrawest.ticker_app.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);

    Optional<User> findByEmail(String userName);

    List<User> getAll();

    User getExistUser(String userName);
}
