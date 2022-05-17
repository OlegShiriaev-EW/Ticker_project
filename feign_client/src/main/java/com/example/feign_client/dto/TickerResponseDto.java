package com.example.feign_client.dto;

import com.example.feign_client.model.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TickerResponseDto {
    private int id;
    private int interval;
    private Status status;
}
