package com.sparta.springintermediateasignment.user.entity;

import com.sparta.springintermediateasignment.schedule.entity.Schedule;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public static ScheduleUser createScheduleManager(Schedule schedule, User user) {
        ScheduleUser manager = new ScheduleUser();
        manager.setSchedule(schedule);
        manager.setUser(user);
        return manager;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
        this.schedule.getScheduleManagers()
            .add(this);
    }

    public void setUser(User user) {
        this.user = user;
        this.user.getSchedulesManagers()
            .add(this);
    }
}
