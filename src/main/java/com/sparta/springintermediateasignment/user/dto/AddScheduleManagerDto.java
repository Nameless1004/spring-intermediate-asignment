package com.sparta.springintermediateasignment.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
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
    private Long userId;
}
