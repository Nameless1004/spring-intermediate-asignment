package com.sparta.springintermediateasignment.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequestDto {

    @NotNull
    private Long userId;
    @NotBlank
    private String todoTitle;
    @NotBlank
    private String todoContents;
}
