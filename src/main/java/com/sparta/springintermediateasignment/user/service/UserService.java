package com.sparta.springintermediateasignment.user.service;

import com.sparta.springintermediateasignment.user.dto.ManagerAddRequestDto;
import com.sparta.springintermediateasignment.user.dto.UserDto;
import java.util.List;

public interface UserService {

    Long save(UserDto userDto);

    void delete(Long id);

    UserDto update(Long id, UserDto userDto);

    List<UserDto> findAll();

    UserDto findById(Long id);

    void addManager(ManagerAddRequestDto userDto);

    void deleteManager(ManagerAddRequestDto userDto);
}
