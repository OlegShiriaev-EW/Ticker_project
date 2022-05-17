package com.extrawest.ticker_app.dto;

import com.extrawest.ticker_app.validate.FieldsValueMatch;
import com.extrawest.ticker_app.validate.ValidEmail;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@FieldsValueMatch(field = "password",
        fieldMatch = "repeatPassword",
        message = "Passwords do not match!")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    @ValidEmail
    private String email;
    @Size(min = 6, max = 12)
    private String password;
    private String repeatPassword;

}
