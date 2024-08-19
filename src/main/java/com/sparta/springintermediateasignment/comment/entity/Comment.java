package com.sparta.springintermediateasignment.comment.entity;

import com.sparta.springintermediateasignment.comment.dto.CommentDto;
import com.sparta.springintermediateasignment.schedule.entity.Schedule;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name="writer_name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @Setter
    @Column(name ="comment_contents", nullable = false)
    private String contents;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdAt;

    @Setter
    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedAt;

    public static Comment of(Schedule schedule, CommentDto dto){
        return Comment.builder()
            .id(dto.getId())
            .name(dto.getWriterName())
            .contents(dto.getContents())
            .createdAt(dto.getCreatedAt())
            .updatedAt(dto.getUpdatedAt())
            .schedule(schedule)
            .build();
    }

}
