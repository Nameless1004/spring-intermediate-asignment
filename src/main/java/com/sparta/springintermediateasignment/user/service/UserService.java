package com.sparta.springintermediateasignment.user.service;

import com.sparta.springintermediateasignment.user.dto.JwtTokenResponseDto;
import com.sparta.springintermediateasignment.user.dto.LoginRequestDto;
import com.sparta.springintermediateasignment.user.dto.ManagerAddRequestDto;
import com.sparta.springintermediateasignment.user.dto.SignupRequestDto;
import com.sparta.springintermediateasignment.user.dto.UserDto;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService {

    Long save(UserDto userDto);

    void delete(Long id);

    UserDto update(Long id, UserDto userDto);

    List<UserDto> findAll();

    UserDto findById(Long id);

    void addManager(ManagerAddRequestDto userDto);

    void deleteManager(ManagerAddRequestDto userDto);

    JwtTokenResponseDto signup(SignupRequestDto requestDto, HttpServletResponse res);

    JwtTokenResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse res);
}
