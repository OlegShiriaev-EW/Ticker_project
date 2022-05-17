package com.extrawest.ticker_app.model;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "ticks_history")
@Data
@NoArgsConstructor
public class TicksHistory {
    @Id
    private String id;
    private Date timestamp;
    private Side side;
    @Field(name = "current_interval")
    private double currentInterval;
    @DBRef
    private Ticker ticker;

    public TicksHistory(Date timestamp, Side side, double currentInterval, Ticker ticker) {
        this.timestamp = timestamp;
        this.side = side;
        this.currentInterval = currentInterval;
        this.ticker = ticker;
    }
}
