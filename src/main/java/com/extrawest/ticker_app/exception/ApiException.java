package com.extrawest.ticker_app.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.ZonedDateTime;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ApiException {
    private final String message;
    private final HttpStatus httpStatus;
    private ZonedDateTime timestamp;
}
