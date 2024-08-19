package com.sparta.springintermediateasignment.domain.entity;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "schedule")
public class Schedule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="schedule_id")
    private Long id;

    @Column(name="user_name", nullable=false)
    private String userName;

    @Column(name="todo_title", nullable=false)
    private String todoTitle;

    @Column(name="todo_contents", nullable=false)
    private String todoContents;

    @Column(name="created_date", nullable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_date", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "schedule")
    private List<Comment> comments = new ArrayList<>();
}
