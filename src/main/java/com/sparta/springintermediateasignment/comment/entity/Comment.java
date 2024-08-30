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

    @Column(name = "author_name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @Column(name = "comment_contents", nullable = false)
    private String contents;


    /**
     * 댓글 엔터티 생성
     *
     * @param schedule 일정
     * @param dto      댓글DTO
     * @return 생성된 엔터티
     */
    public static Comment createComment(Schedule schedule, CommentDto dto) {
        Comment comment = Comment.builder()
            .id(dto.getCommentId())
            .name(dto.getAuthorName())
            .contents(dto.getContents())
            .schedule(schedule)
            .build();

        schedule.getComments()
            .add(comment);
        return comment;
    }

    /**
     * 댓글 업데이트
     *
     * @param contents 수정 내용
     */
    public void update(String contents) {
        this.contents = contents;
    }

}
