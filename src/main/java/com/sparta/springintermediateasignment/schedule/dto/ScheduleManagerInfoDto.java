package com.sparta.springintermediateasignment.schedule.dto;

import com.sparta.springintermediateasignment.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleManagerInfoDto {

    private Long id;
    private String name;
    private String email;

    public static ScheduleManagerInfoDto createScheduleManagerInfoDto(User user) {
        ScheduleManagerInfoDto dto = new ScheduleManagerInfoDto();

        dto.id = user.getId();
        dto.name = user.getName();
        dto.email = user.getEmail();
        return dto;
    }
}
