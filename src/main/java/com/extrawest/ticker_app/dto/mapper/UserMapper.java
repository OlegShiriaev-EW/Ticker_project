package com.extrawest.ticker_app.dto.mapper;

import com.extrawest.ticker_app.dto.UserResponseDto;
import com.extrawest.ticker_app.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseDto toDto(User user) {
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(user.getId());
        responseDto.setEmail(user.getEmail());
        return responseDto;
    }
}
