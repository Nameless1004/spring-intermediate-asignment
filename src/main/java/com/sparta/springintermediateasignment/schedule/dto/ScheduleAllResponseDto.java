package com.sparta.springintermediateasignment.schedule.dto;


import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class ScheduleAllResponseDto {

    private Long scheduleId;
    private Long userId;
    private String scheduleTitle;
    private String scheduleContents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
