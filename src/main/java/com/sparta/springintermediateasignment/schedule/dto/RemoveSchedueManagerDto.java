package com.sparta.springintermediateasignment.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RemoveSchedueManagerDto {

    @NotNull
    Long authorId;
}
