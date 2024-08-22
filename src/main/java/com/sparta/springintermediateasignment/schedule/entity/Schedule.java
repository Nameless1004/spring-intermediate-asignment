package com.sparta.springintermediateasignment.schedule.entity;

import com.sparta.springintermediateasignment.comment.dto.CommentDto;
import com.sparta.springintermediateasignment.comment.entity.Comment;
import com.sparta.springintermediateasignment.common.BaseTimeEntity;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleManagerInfoDto;
import com.sparta.springintermediateasignment.user.entity.ScheduleUser;
import com.sparta.springintermediateasignment.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(name = "schedule")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Setter
    @Column(name = "todo_title", nullable = false)
    private String todoTitle;

    @Setter
    @Column(name = "todo_contents", nullable = false)
    private String todoContents;

    @Setter
    private String weather;

    // 부모 삭제되면 자식(Comment)도 같이 삭제되게 처리
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Comment> comments = new ArrayList<>();

    // 담당 유저
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ScheduleUser> scheduleManagers = new ArrayList<>();

    public static Schedule createSchedule(User user, String todoTitle, String todoContents,
        String weather) {
        Schedule schedule = new Schedule();
        schedule.setUser(user);
        schedule.setTodoTitle(todoTitle);
        schedule.setTodoContents(todoContents);
        schedule.setWeather(weather);
        return schedule;
    }

    public void setUser(User user) {
        this.user = user;
        user.getSchedules()
            .add(this);
    }

    public Long getUserId() {
        return user.getId();
    }

    public List<CommentDto> getCommentList() {
        return comments.stream()
            .map(CommentDto::createCommentDto)
            .toList();
    }

    public List<ScheduleManagerInfoDto> getManagerList() {
        return scheduleManagers.stream()
            .map(scheduleManager -> ScheduleManagerInfoDto.createScheduleManagerInfoDto(
                scheduleManager.getUser()))
            .toList();
    }
}
