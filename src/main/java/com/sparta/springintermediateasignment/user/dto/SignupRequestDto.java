package com.sparta.springintermediateasignment.user.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class SignupRequestDto {

    private String username;
    private String password;
    private String email;
    private boolean admin = false;
    private String adminToken = "";
}
