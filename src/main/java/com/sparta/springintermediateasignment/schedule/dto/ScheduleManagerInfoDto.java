package com.sparta.springintermediateasignment.schedule.dto;

import com.sparta.springintermediateasignment.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
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

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
