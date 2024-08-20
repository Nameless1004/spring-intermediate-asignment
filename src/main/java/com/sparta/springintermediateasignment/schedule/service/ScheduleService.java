package com.sparta.springintermediateasignment.schedule.service;

import com.sparta.springintermediateasignment.schedule.dto.ScheduleAllResponseDto;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleRequestDto;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleResponseDto;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleUpdateDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ScheduleService {

    Long saveSchedule(ScheduleRequestDto scheduleRequestDto);

    ScheduleResponseDto getScheduleById(Long id);

    ScheduleResponseDto updateSchedule(Long id, ScheduleUpdateDto scheduleRequestDto);

    List<ScheduleAllResponseDto> getAllSchedule(Pageable pageable);

    void delete(Long id);
}
