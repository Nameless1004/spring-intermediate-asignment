package com.sparta.springintermediateasignment.user.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class LoginRequestDto {

    private String email;
    private String password;
}
