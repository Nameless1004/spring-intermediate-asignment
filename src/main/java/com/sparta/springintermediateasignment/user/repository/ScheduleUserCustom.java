package com.sparta.springintermediateasignment.user.repository;

import com.sparta.springintermediateasignment.user.entity.ScheduleUser;
import com.sparta.springintermediateasignment.user.entity.User;
import java.util.List;
import java.util.Optional;

public interface ScheduleUserCustom {
     List<User> findUsersByScheduleId(Long scheduleId);

     void deleteByScheduleId(Long scheduleId);
     void deleteByUserId(Long userId);

}
