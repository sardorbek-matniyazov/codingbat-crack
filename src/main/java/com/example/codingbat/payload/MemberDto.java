package com.example.codingbat.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class MemberDto {
    @Email(message = "email is not valid")
    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "password is required")
    private String password;
    @NotBlank(message = "must type pre password, it is required")
    private String prePassword;
}
