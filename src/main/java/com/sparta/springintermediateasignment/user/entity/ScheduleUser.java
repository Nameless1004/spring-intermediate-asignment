package com.sparta.springintermediateasignment.user.entity;

import com.sparta.springintermediateasignment.schedule.entity.Schedule;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ScheduleUser {

    @Id
    @GeneratedValue
    private Long id;


    @Column(name = "user_id")
    private Long userId;

    @Column(name = "schedule_id")
    private Long scheduleId;

    public static ScheduleUser createScheduleManager(Long scheduleId, Long userId) {
        ScheduleUser manager = new ScheduleUser();
        manager.scheduleId = scheduleId;
        manager.userId = userId;
        return manager;
    }

}
