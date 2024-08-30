package com.sparta.springintermediateasignment.comment.dto;

import com.sparta.springintermediateasignment.comment.entity.Comment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long commentId;
    @NotNull
    private Long scheduleId;
    @NotBlank
    private String authorName;
    @NotBlank
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * 댓글 DTO 생성
     *
     * @param comment 댓글 엔터티
     * @return
     */
    public static CommentDto createCommentDto(Comment comment) {
        return CommentDto.builder()
            .commentId(comment.getId())
            .scheduleId(comment.getSchedule()
                .getId())
            .authorName(comment.getName())
            .contents(comment.getContents())
            .createdAt(comment.getCreatedDate())
            .updatedAt(comment.getUpdatedDate())
            .build();
    }
}
