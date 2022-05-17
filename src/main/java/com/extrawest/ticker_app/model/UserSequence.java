package com.extrawest.ticker_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_sequence")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSequence {
    @Id
    private String id;
    private int sequence;
}
