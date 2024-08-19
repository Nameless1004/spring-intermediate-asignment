package com.sparta.springintermediateasignment.comment.dto;

import com.sparta.springintermediateasignment.comment.entity.Comment;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class CommentDto {
    private Long id;
    private Long scheduleId;
    @NotBlank
    private String writerName;
    @NotBlank
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CommentDto of(Comment comment) {
        return CommentDto.builder()
            .id(comment.getId())
            .scheduleId(comment.getSchedule().getId())
            .writerName(comment.getName())
            .contents(comment.getContents())
            .createdAt(comment.getCreatedAt())
            .updatedAt(comment.getUpdatedAt())
            .build();
    }
}
