package com.sparta.springintermediateasignment.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScheduleUpdateDto {

    @NotBlank
    private String title;
    @NotBlank
    private String contents;
}