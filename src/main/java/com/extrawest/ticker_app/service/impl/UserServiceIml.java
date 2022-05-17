package com.extrawest.ticker_app.service.impl;

import com.extrawest.ticker_app.exception.ApiRequestException;
import com.extrawest.ticker_app.model.User;
import com.extrawest.ticker_app.repository.UserRepository;
import com.extrawest.ticker_app.service.UserService;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceIml implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserSequenceGeneratorService generatorService;

    @Override
    public User save(User user) {
        user.setId(generatorService.getSequenceNumber(User.SEQUENCE_NAME));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getExistUser(String userName) {
        return findByEmail(userName).orElseThrow(() ->
                new ApiRequestException("User was not found!"));
    }
}
