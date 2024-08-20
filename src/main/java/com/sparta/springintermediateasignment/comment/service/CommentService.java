package com.sparta.springintermediateasignment.comment.service;

import com.sparta.springintermediateasignment.comment.dto.CommentDto;
import com.sparta.springintermediateasignment.comment.dto.CommentUpdateDto;
import java.util.List;

public interface CommentService {
    Long save(CommentDto commentRequestDto);
    CommentDto update(Long commentId, CommentUpdateDto commentRequestDto);
    void delete(Long commentId);
    CommentDto findById(Long id);
    List<CommentDto> findAll();
}
