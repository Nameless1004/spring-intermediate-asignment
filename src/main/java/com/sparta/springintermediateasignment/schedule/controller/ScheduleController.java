package com.sparta.springintermediateasignment.schedule.controller;

import com.sparta.springintermediateasignment.schedule.dto.ScheduleAllResponseDto;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleRequestDto;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleResponseDto;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleUpdateDto;
import com.sparta.springintermediateasignment.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService service;

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable("scheduleId") Long scheduleId) {
        return ResponseEntity.ok(service.getScheduleById(scheduleId));
    }

    @GetMapping
    public ResponseEntity<List<ScheduleAllResponseDto>> getSchedules(
        @PageableDefault(size = 10, sort = "updatedDate", direction = Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(service.getAllSchedule(pageable));
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> postSchedule(
        @Valid @RequestBody ScheduleRequestDto schedule) {
        Long id = service.saveSchedule(schedule);
        ScheduleResponseDto scheduleResponseDto = service.getScheduleById(id);
        return ResponseEntity.ok(scheduleResponseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> patchSchedule(@PathVariable("id") Long id,
        @Valid @RequestBody ScheduleUpdateDto schedule) {
        return ResponseEntity.ok(service.updateSchedule(id, schedule));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable("id") Long id) {
        service.delete(id);

        return ResponseEntity.ok()
            .build();
    }

}
