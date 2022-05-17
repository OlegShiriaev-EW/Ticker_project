package com.extrawest.ticker_app.dto;

import com.extrawest.ticker_app.model.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TickerResponseDto {
    private int id;
    private int interval;
    private Status status;
}
