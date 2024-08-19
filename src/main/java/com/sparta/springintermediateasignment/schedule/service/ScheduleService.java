package com.sparta.springintermediateasignment.schedule.service;

import com.sparta.springintermediateasignment.schedule.dto.ScheduleDto;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleUpdateDto;

public interface ScheduleService {
    Long save(ScheduleDto scheduleRequestDto);
    ScheduleDto findById(Long id);
    ScheduleDto update(Long id, ScheduleUpdateDto scheduleRequestDto);
}
