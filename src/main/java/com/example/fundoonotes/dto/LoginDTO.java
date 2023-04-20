package com.example.fundoonotes.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class LoginDTO {

    @Pattern(regexp = "^[a-zA-Z\\d+_.-]+@[a-zA-Z]+.[a-zA-z]{2,}")
    private String emailId;

    @Pattern(regexp = "(?=.*[A-Z])" + "(?=.*[0-9])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$)" + ".{8,}"
            ,message = "password has at least 8 character one uppercase letter one numeric number" +
            " and one special character and no whitespace allowed between characters")
    private String password;


}
