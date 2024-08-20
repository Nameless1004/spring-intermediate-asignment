package com.sparta.springintermediateasignment.comment.entity;

import com.sparta.springintermediateasignment.comment.dto.CommentDto;
import com.sparta.springintermediateasignment.common.BaseTimeEntity;
import com.sparta.springintermediateasignment.schedule.entity.Schedule;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "comment")
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Setter
    @Column(name = "writer_name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @Setter
    @Column(name = "comment_contents", nullable = false)
    private String contents;


    public static Comment of(Schedule schedule, CommentDto dto) {
        return Comment.builder()
            .id(dto.getCommentId())
            .name(dto.getWriterName())
            .contents(dto.getContents())
            .schedule(schedule)
            .build();
    }

}
