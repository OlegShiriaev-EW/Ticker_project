package com.extrawest.ticker_app.model;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "tick_statistic")
@NoArgsConstructor
@Data
public class TickStatistic {
    @Id
    private String id;
    private Date timestamp;
    private int tickerId;
    private int userId;
    private Status status;

    public TickStatistic(Date timestamp, int tickerId, Status status, int userId) {
        this.timestamp = timestamp;
        this.tickerId = tickerId;
        this.status = status;
        this.userId = userId;
    }
}
