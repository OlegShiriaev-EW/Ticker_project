package com.extrawest.ticker_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tickers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticker {
    @Transient
    public static final String SEQUENCE_NAME = "ticker_sequence";
    @Id
    private int id;
    private int interval;
    @DBRef
    private User user;
    private Status status;

    public Ticker(int interval, User user, Status status) {
        this.interval = interval;
        this.user = user;
        this.status = status;
    }
}
