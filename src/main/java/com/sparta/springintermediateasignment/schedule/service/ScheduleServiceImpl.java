package com.sparta.springintermediateasignment.schedule.service;

import com.sparta.springintermediateasignment.schedule.dto.ScheduleDto;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleUpdateDto;
import com.sparta.springintermediateasignment.schedule.entity.Schedule;
import com.sparta.springintermediateasignment.schedule.repository.ScheduleRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository repository;

    @Override
    public Long save(ScheduleDto scheduleRequestDto) {
        scheduleRequestDto.setCreatedAt(LocalDateTime.now());
        scheduleRequestDto.setUpdatedAt(LocalDateTime.now());
        Schedule schedule = Schedule.of(scheduleRequestDto);
        repository.save(schedule);
        return schedule.getId();
    }

    @Override
    public ScheduleDto findById(Long id) {
        Schedule schedule = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        return ScheduleDto.of(schedule);
    }

    @Override
    @Transactional
    public ScheduleDto update(Long id, ScheduleUpdateDto scheduleRequestDto) {
        Schedule schedule = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        schedule.setTodoTitle(scheduleRequestDto.getTitle());
        schedule.setTodoContents(scheduleRequestDto.getContents());
        schedule.setUpdatedAt(LocalDateTime.now());

        repository.save(schedule);

        return ScheduleDto.of(schedule);
    }
}
