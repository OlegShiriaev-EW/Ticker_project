package com.extrawest.ticker_app.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
@NoArgsConstructor
public class User {
    @Transient
    public static final String SEQUENCE_NAME = "user_sequence";
    @Id
    private int id;
    @Indexed(unique = true)
    private String email;
    private String password;
    private Role role;

    public User(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String email) {
        this.email = email;
    }
}
