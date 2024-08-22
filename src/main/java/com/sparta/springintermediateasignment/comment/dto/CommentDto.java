package com.sparta.springintermediateasignment.comment.dto;

import com.sparta.springintermediateasignment.comment.entity.Comment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class CommentDto {

    private Long commentId;
    @NotNull
    private Long scheduleId;
    @NotBlank
    private String writerName;
    @NotBlank
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CommentDto createCommentDto(Comment comment) {
        return CommentDto.builder()
            .commentId(comment.getId())
            .scheduleId(comment.getSchedule()
                .getId())
            .writerName(comment.getName())
            .contents(comment.getContents())
            .createdAt(comment.getCreatedDate())
            .updatedAt(comment.getUpdatedDate())
            .build();
    }
}
