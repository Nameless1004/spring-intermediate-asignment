package com.sparta.springintermediateasignment.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class LoginRequestDto {

    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
