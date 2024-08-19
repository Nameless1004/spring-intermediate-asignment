package com.sparta.springintermediateasignment.schedule.controller;

import com.sparta.springintermediateasignment.schedule.dto.ScheduleDto;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleUpdateDto;
import com.sparta.springintermediateasignment.schedule.service.ScheduleService;
import com.sparta.springintermediateasignment.schedule.service.ScheduleServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService service;

    @GetMapping("/{scheduleId}")
    public ScheduleDto getSchedule(@PathVariable Long scheduleId) {
        return service.findById(scheduleId);
    }

    @PostMapping
    public Long postSchedule(@RequestBody ScheduleDto schedule) {
        // 혹시라도 등록할 때 id값이 지정되어있으면 null로 해주기
        schedule.setId(null);
        Long id = service.save(schedule);
        return id;
    }

    @PatchMapping("/{id}")
    public ScheduleDto patchSchedule(@PathVariable Long id, @Valid  @RequestBody ScheduleUpdateDto schedule) {
        return service.update(id, schedule);
    }
}
