package com.sparta.springintermediateasignment.manager.repository;

import com.sparta.springintermediateasignment.user.entity.ScheduleUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleUserRepository extends JpaRepository<ScheduleUser, Long>,
    ScheduleUserCustom {

    boolean existsByScheduleIdAndUserId(Long scheduleId, Long userId);
}

