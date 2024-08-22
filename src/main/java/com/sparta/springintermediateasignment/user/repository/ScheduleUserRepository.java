package com.sparta.springintermediateasignment.user.repository;

import com.sparta.springintermediateasignment.user.entity.ScheduleUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleUserRepository extends JpaRepository<ScheduleUser, Long> {

    Optional<ScheduleUser> findByUserId(Long userId);

    Optional<ScheduleUser> findByUserIdAndScheduleId(Long userId, Long scheduleId);
}

