package com.sparta.springintermediateasignment.schedule.dto;

import com.sparta.springintermediateasignment.comment.dto.CommentDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class ScheduleResponseDto {

    private Long scheduleId;
    private Long userId;
    private String scheduleTitle;
    private String scheduleContents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<CommentDto> commentList;
    private List<ScheduleManagerInfoDto> managerList;
}
