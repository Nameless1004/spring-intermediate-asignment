package com.sparta.springintermediateasignment.manager.repository;

import com.sparta.springintermediateasignment.schedule.dto.ScheduleManagerInfoDto;
import com.sparta.springintermediateasignment.user.entity.ScheduleUser;
import java.util.List;
import java.util.Optional;

public interface ScheduleUserCustom {

    List<ScheduleManagerInfoDto> findUsersByScheduleId(Long scheduleId);

    Optional<ScheduleUser> findByUserIdAndScheduleId(Long userId, Long scheduleId);
}
