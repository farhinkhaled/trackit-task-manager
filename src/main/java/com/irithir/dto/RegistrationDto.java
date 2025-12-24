package com.irithir.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
public class RegistrationDto {
    private Long id;
    @NotEmpty(message = "Please enter your username")
    private String username;
    @NotEmpty(message = "Please enter your email address")
    private String email;
    @NotEmpty(message = "Please enter your password")
    private String password;
}
