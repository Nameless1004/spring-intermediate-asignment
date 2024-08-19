package com.sparta.springintermediateasignment.comment.service;

import com.sparta.springintermediateasignment.comment.dto.CommentDto;
import java.util.List;

public interface CommentService {
    Long save(Long scheduleId, CommentDto commentRequestDto);
    CommentDto update(Long commentId, CommentDto commentRequestDto);
    void delete(Long commentId);
    CommentDto findById(Long id);
    List<CommentDto> findAll();
}
