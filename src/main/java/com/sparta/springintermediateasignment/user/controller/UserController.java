package com.sparta.springintermediateasignment.user.controller;

import com.sparta.springintermediateasignment.user.dto.JwtTokenResponseDto;
import com.sparta.springintermediateasignment.user.dto.LoginRequestDto;
import com.sparta.springintermediateasignment.user.dto.ManagerAddRequestDto;
import com.sparta.springintermediateasignment.user.dto.SignupRequestDto;
import com.sparta.springintermediateasignment.user.dto.UserDto;
import com.sparta.springintermediateasignment.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<JwtTokenResponseDto> saveUser(@Valid @RequestBody SignupRequestDto requestDto) {
        JwtTokenResponseDto response = service.join(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> patchUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        return new ResponseEntity<>(service.update(id, userDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok()
            .build();
    }

    // 일정 담당자 등록
    @PostMapping("/schedules")
    public ResponseEntity<Void> addScheduleManager(@Valid @RequestBody ManagerAddRequestDto userDto) {
        service.addManager(userDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .build();
    }

    // 일정 담당자 삭제
    @DeleteMapping("/schedules")
    public ResponseEntity<Void> deleteScheduleManager(@Valid @RequestBody ManagerAddRequestDto userDto) {
        service.deleteManager(userDto);
        return ResponseEntity.ok()
            .build();
    }

    @PostMapping("/signup")
    public ResponseEntity<JwtTokenResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        JwtTokenResponseDto token = service.join(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(token);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        JwtTokenResponseDto token = service.login(loginRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
            .body(token);
    }
}
