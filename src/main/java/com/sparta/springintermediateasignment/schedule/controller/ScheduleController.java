package com.sparta.springintermediateasignment.schedule.controller;

import com.sparta.springintermediateasignment.comment.dto.CommentDto;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleDto;
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

    @GetMapping
    public List<ScheduleDto> getSchedules(@PageableDefault( size = 10, sort = "updatedAt", direction = Direction.DESC) Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/{scheduleId}/comments")
    public List<CommentDto> getComments(@PathVariable Long scheduleId) {
        return service.findById(scheduleId).getComments();
    }

    @PostMapping
    public Long postSchedule(@RequestBody ScheduleDto schedule) {
        Long id = service.save(schedule);
        return id;
    }

    @PatchMapping("/{id}")
    public ScheduleDto patchSchedule(@PathVariable Long id, @Valid  @RequestBody ScheduleUpdateDto schedule) {
        return service.update(id, schedule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> patchSchedule(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
