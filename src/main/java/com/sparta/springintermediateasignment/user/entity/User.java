package com.sparta.springintermediateasignment.user.entity;

import com.sparta.springintermediateasignment.common.BaseTimeEntity;
import com.sparta.springintermediateasignment.schedule.entity.Schedule;
import com.sparta.springintermediateasignment.user.dto.UserDto;
import com.sparta.springintermediateasignment.user.enums.UserRole;
import jakarta.persistence.CascadeType;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    // 유저가 작성한 스케쥴
    // 유저가 삭제되면 작성한 스케쥴도 삭제처리
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules = new ArrayList<>();

    // 담당 유저 스케쥴
    // 유저가 삭제되면 담당 스케쥴에서 유저 삭제
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScheduleManager> schedulesManagers = new ArrayList<>();

    public static User of(UserDto dto) {
        return User.builder()
            .name(dto.getName())
            .email(dto.getEmail())
            .role(dto.getRole())
            .build();
    }
}
