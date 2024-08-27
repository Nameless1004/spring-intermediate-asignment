package com.sparta.springintermediateasignment.user.entity;

import com.sparta.springintermediateasignment.common.BaseTimeEntity;
import com.sparta.springintermediateasignment.schedule.entity.Schedule;
import com.sparta.springintermediateasignment.user.dto.UserDto;
import com.sparta.springintermediateasignment.user.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "user_password", nullable = false)
    private String password;
    @Column(name = "user_name", nullable = false)
    private String name;
    @Column(name = "user_email", nullable = false)
    private String email;

    @Enumerated(value = EnumType.STRING)
    UserRole role;

    @OneToMany(mappedBy = "author")
    private List<Schedule> createdSchedules = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ScheduleUser> managedSchedules = new ArrayList<>();

    public static User createUser(UserDto dto) {
        return User.builder()
            .name(dto.getName())
            .email(dto.getEmail())
            .role(dto.getRole())
            .build();
    }

    public void addManagedSchedule(ScheduleUser scheduleUser) {
        managedSchedules.add(scheduleUser);
    }

    public void removeManagedSchedule(ScheduleUser scheduleUser) {
        managedSchedules.remove(scheduleUser);
    }

    public void addSchedule(Schedule schedule) {
        createdSchedules.add(schedule);
    }

    public void removeSchedule(Schedule schedule) {
        createdSchedules.remove(schedule);
    }

    public void update(UserDto userDto) {
        name = userDto.getName();
        role = userDto.getRole();
        email = userDto.getEmail();
    }
}
