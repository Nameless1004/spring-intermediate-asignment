package com.sparta.springintermediateasignment.schedule.dto;

import com.sparta.springintermediateasignment.comment.dto.CommentDto;
import com.sparta.springintermediateasignment.schedule.entity.Schedule;
import com.sparta.springintermediateasignment.user.dto.ManagerDto;
import com.sparta.springintermediateasignment.user.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScheduleFindDto {

    private Long id;
    private Long userId;
    private String todoTitle;
    private String todoContents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentDto> comments;
    private List<ManagerDto> schedulers;

    public static ScheduleFindDto of(User user, List<CommentDto> commentDtos, List<ManagerDto> managerDtos, Schedule schedule) {
        return ScheduleFindDto.builder()
            .id(schedule.getId())
            .userId(user.getId())
            .todoTitle(schedule.getTodoTitle())
            .todoContents(schedule.getTodoContents())
            .createdAt(schedule.getCreatedDate())
            .updatedAt(schedule.getUpdatedDate())
            .comments(commentDtos)
            .schedulers(managerDtos)
            .build();
    }
}
