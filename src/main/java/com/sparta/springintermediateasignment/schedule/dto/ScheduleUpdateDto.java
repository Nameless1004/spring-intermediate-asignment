package com.sparta.springintermediateasignment.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

@Getter
public class ScheduleUpdateDto {

    @NotBlank
    private String title;
    @NotBlank
    private String contents;
}