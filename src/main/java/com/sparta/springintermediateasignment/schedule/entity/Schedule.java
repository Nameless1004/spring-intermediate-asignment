package com.sparta.springintermediateasignment.schedule.entity;

import com.sparta.springintermediateasignment.comment.entity.Comment;
import com.sparta.springintermediateasignment.common.BaseTimeEntity;
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
    private User author;

    @Column(name = "todo_title", nullable = false)
    private String todoTitle;

    @Column(name = "todo_contents", nullable = false)
    private String todoContents;

    private String weather;

    // 댓글은 일정에 종속되기 때문에 같이 삭제 처리해준다.
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // 조인 테이블은 Cascase X
    @OneToMany(mappedBy = "schedule")
    private List<ScheduleUser> managedUser = new ArrayList<>();

    public static Schedule createSchedule(User author, String todoTitle, String todoContents,
        String weather) {
        Schedule schedule = new Schedule();
        schedule.author = author;
        schedule.todoTitle = todoTitle;
        schedule.todoContents = todoContents;
        schedule.weather = weather;

        author.addSchedule(schedule);
        return schedule;
    }


    public void update(String title, String contents){
        this.todoTitle = title;
        this.todoContents = contents;
    }

    public void removeManager(ScheduleUser scheduleUser) {
        managedUser.remove(scheduleUser);
    }

    public void addManager(ScheduleUser scheduleUser){
        managedUser.add(scheduleUser);
    }
}
