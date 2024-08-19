package com.sparta.springintermediateasignment.schedule.entity;

import com.sparta.springintermediateasignment.comment.entity.Comment;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "schedule")
public class Schedule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="schedule_id")
    private Long id;

    @Column(name="user_name", nullable=false)
    private String userName;

    @Setter
    @Column(name="todo_title", nullable=false)
    private String todoTitle;

    @Setter
    @Column(name="todo_contents", nullable=false)
    private String todoContents;

    @Column(name="created_date", nullable = false)
    private LocalDateTime createdAt;

    @Setter
    @Column(name="updated_date", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "schedule")
    private List<Comment> comments = new ArrayList<>();

    public static Schedule of(ScheduleDto dto){
        return Schedule.builder()
            .id(dto.getId())
            .userName(dto.getUserName())
            .todoTitle(dto.getTodoTitle())
            .todoContents(dto.getTodoContents())
            .createdAt(dto.getCreatedAt())
            .updatedAt(dto.getUpdatedAt())
            .build();
    }
}
