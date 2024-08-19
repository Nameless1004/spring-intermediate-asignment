package com.sparta.springintermediateasignment.schedule.service;

import com.sparta.springintermediateasignment.schedule.dto.ScheduleDto;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleUpdateDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ScheduleService {
    Long save(ScheduleDto scheduleRequestDto);
    ScheduleDto findById(Long id);
    ScheduleDto update(Long id, ScheduleUpdateDto scheduleRequestDto);

    List<ScheduleDto> findAll(Pageable pageable);
}
