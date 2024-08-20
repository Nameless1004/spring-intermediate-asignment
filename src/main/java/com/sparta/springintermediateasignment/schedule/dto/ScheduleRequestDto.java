package com.sparta.springintermediateasignment.schedule.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class ScheduleRequestDto {

    private Long userId;
    private String todoTitle;
    private String todoContents;
}
