package com.sparta.springintermediateasignment.comment.repository;

import com.sparta.springintermediateasignment.comment.entity.Comment;
import java.util.List;

public interface CommentCustom {

    List<Comment> findByScheduleId(Long scheduleId);
}
