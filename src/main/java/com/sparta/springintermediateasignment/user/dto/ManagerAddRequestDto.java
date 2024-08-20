package com.sparta.springintermediateasignment.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ManagerAddRequestDto {
    private Long scheduleId;
    private Long managerId;
}
