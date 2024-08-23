package com.sparta.springintermediateasignment.schedule.repository;

import com.sparta.springintermediateasignment.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleCustom{

}
