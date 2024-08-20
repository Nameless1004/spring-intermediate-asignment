package com.sparta.springintermediateasignment.schedule.dto;

import com.sparta.springintermediateasignment.user.entity.User;
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

    public ScheduleManagerInfoDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
