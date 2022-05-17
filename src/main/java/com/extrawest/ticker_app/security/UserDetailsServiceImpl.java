package com.extrawest.ticker_app.security;

import com.extrawest.ticker_app.exception.ApiRequestException;
import com.extrawest.ticker_app.model.User;
import com.extrawest.ticker_app.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component("userDetailsServiceImpl")
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new ApiRequestException("User with email " + email + " doesn't exist!"));
        return SecurityUser.fromUser(user);
    }
}
