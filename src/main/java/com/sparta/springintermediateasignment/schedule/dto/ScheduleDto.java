package com.sparta.springintermediateasignment.schedule.dto;

import com.sparta.springintermediateasignment.comment.dto.CommentDto;
import com.sparta.springintermediateasignment.schedule.entity.Schedule;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class ScheduleDto {
    private Long id;
    private String userName;
    private String todoTitle;
    private String todoContents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentDto> comments;

    public static ScheduleDto of(Schedule schedule) {
        return ScheduleDto.builder()
            .id(schedule.getId())
            .userName(schedule.getUserName())
            .todoTitle(schedule.getTodoTitle())
            .todoContents(schedule.getTodoContents())
            .createdAt(schedule.getCreatedAt())
            .updatedAt(schedule.getUpdatedAt())
            .comments(schedule.getComments().stream().map(CommentDto::of).toList())
            .build();
    }
}
