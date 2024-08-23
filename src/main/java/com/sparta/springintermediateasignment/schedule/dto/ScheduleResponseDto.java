package com.sparta.springintermediateasignment.schedule.dto;

import com.sparta.springintermediateasignment.comment.dto.CommentDto;
import java.lang.annotation.Documented;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponseDto {

    private Long scheduleId;
    private Long userId;
    private String scheduleTitle;
    private String scheduleContents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public List<ScheduleManagerInfoDto> managaers;
}
