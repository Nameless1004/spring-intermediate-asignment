package com.sparta.springintermediateasignment.schedule.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddScheduleManagerDto {

    @NotNull
    private Long authorUserId;

    @NotNull
    private Long scheduleId;

    @NotNull
    private Long managerId;
}
