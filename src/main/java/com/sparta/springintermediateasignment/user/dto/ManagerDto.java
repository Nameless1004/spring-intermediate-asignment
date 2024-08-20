package com.sparta.springintermediateasignment.user.dto;

import com.sparta.springintermediateasignment.user.entity.ScheduleManager;
import com.sparta.springintermediateasignment.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ManagerDto {

    private Long id;
    private String username;
    private String email;

    public ManagerDto(ScheduleManager scheduleUser) {
        User user = scheduleUser.getUser();
        id = user.getId();
        username = user.getEmail();
        email = user.getEmail();
    }
}
