package com.sparta.springintermediateasignment.schedule.entity;

import com.sparta.springintermediateasignment.comment.entity.Comment;
import com.sparta.springintermediateasignment.common.BaseTimeEntity;
import com.sparta.springintermediateasignment.user.entity.ScheduleManager;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Table(name = "schedule")
@NoArgsConstructor
public class Schedule extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="schedule_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Setter
    @Column(name="todo_title", nullable=false)
    private String todoTitle;

    @Setter
    @Column(name="todo_contents", nullable=false)
    private String todoContents;

    @Setter
    private String weather;

    // 부모 삭제되면 자식(Comment)도 같이 삭제되게 처리
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // 담당 유저 조회
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScheduleManager> scheduleManagers = new ArrayList<>();

    public Schedule(User user, String todoTitle, String todoContents) {
        this.user = user;
        this.todoTitle = todoTitle;
        this.todoContents = todoContents;
    }
}
