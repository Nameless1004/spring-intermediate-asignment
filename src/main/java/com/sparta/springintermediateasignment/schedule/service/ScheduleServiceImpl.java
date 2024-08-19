package com.sparta.springintermediateasignment.schedule.service;

import com.sparta.springintermediateasignment.comment.repository.CommentRepository;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleDto;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleUpdateDto;
import com.sparta.springintermediateasignment.schedule.entity.Schedule;
import com.sparta.springintermediateasignment.schedule.repository.ScheduleRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public Long save(ScheduleDto scheduleRequestDto) {
        scheduleRequestDto.setId(null);
        scheduleRequestDto.setCreatedAt(LocalDateTime.now());
        scheduleRequestDto.setUpdatedAt(LocalDateTime.now());
        Schedule schedule = Schedule.of(scheduleRequestDto);
        scheduleRepository.save(schedule);
        return schedule.getId();
    }

    @Override
    public ScheduleDto findById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        return ScheduleDto.of(schedule);
    }

    @Override
    @Transactional
    public ScheduleDto update(Long id, ScheduleUpdateDto scheduleRequestDto) {
        Schedule schedule = scheduleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        schedule.setTodoTitle(scheduleRequestDto.getTitle());
        schedule.setTodoContents(scheduleRequestDto.getContents());
        schedule.setUpdatedAt(LocalDateTime.now());

        scheduleRepository.save(schedule);

        return ScheduleDto.of(schedule);
    }

    @Override
    public List<ScheduleDto> findAll(Pageable pageable) {
        return scheduleRepository.findAll(pageable)
            .stream()
            .map(ScheduleDto::of)
            .toList();
    }

    @Override
    public void delete(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id입니다."));

        scheduleRepository.delete(schedule);
    }

}
