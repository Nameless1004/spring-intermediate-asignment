package com.sparta.springintermediateasignment.user.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
public class LoginRequestDto {
    private String email;
    private String password;
}
