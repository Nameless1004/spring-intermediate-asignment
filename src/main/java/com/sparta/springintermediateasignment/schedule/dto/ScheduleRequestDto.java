package com.sparta.springintermediateasignment.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class ScheduleRequestDto {

    @NotNull
    private Long userId;
    @NotBlank
    private String todoTitle;
    @NotBlank
    private String todoContents;
}
