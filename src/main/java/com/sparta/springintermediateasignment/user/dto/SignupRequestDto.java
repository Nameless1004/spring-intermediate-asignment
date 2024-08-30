package com.sparta.springintermediateasignment.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @Email
    private String email;
    private boolean admin = false;
    private String adminToken = "";
}
