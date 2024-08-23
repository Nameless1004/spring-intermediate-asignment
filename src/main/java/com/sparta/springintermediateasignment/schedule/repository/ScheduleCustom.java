package com.sparta.springintermediateasignment.schedule.repository;

import com.sparta.springintermediateasignment.comment.entity.Comment;
import com.sparta.springintermediateasignment.schedule.entity.Schedule;
import java.util.List;
import java.util.Optional;

public interface ScheduleCustom {
    List<Comment> findCommentsByScheduleId(Long scheduleId);
}
