package com.sparta.springintermediateasignment.manager.controller;

import com.sparta.springintermediateasignment.manager.service.ManagerService;
import com.sparta.springintermediateasignment.schedule.dto.AddScheduleManagerDto;
import com.sparta.springintermediateasignment.schedule.dto.RemoveSchedueManagerDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService service;

    // 일정 담당자 등록
    @PostMapping("/api/managers")
    public ResponseEntity<Void> addScheduleManager(
        @Valid @RequestBody AddScheduleManagerDto userDto) {
        service.registManager(userDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .build();
    }

    // 일정 담당자 삭제
    @DeleteMapping("/api/managers")
    public ResponseEntity<Void> deleteScheduleManager(
        @Valid @RequestBody RemoveSchedueManagerDto userDto) {
        service.unregistManager(userDto);
        return ResponseEntity.ok()
            .build();
    }
}
