package com.sparta.springintermediateasignment.comment.service;

import com.sparta.springintermediateasignment.comment.dto.CommentDto;
import com.sparta.springintermediateasignment.comment.dto.CommentUpdateDto;
import com.sparta.springintermediateasignment.comment.entity.Comment;
import com.sparta.springintermediateasignment.comment.repository.CommentRepository;
import com.sparta.springintermediateasignment.common.exceptoins.InvalidIdException;
import com.sparta.springintermediateasignment.schedule.entity.Schedule;
import com.sparta.springintermediateasignment.schedule.repository.ScheduleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    @Transactional(readOnly = false)
    public Long save(CommentDto commentRequestDto) {
        Schedule schedule = scheduleRepository.findById(commentRequestDto.getScheduleId())
            .orElseThrow(() -> new InvalidIdException("일정"));

        Comment comment = Comment.of(schedule, commentRequestDto);
        commentRepository.save(comment);

        System.out.println("size = " + schedule.getComments()
            .size());
        return comment.getId();
    }

    @Override
    @Transactional(readOnly = false)
    public CommentDto update(Long commentId, CommentUpdateDto commentRequestDto) {
        Comment comment = getComment(commentId);

        comment.setContents(commentRequestDto.getContents());

        commentRepository.save(comment);

        return CommentDto.of(comment);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long commentId) {
        Comment comment = getComment(commentId);
        commentRepository.delete(comment);
    }

    @Override
    public CommentDto findById(Long commentId) {
        Comment comment = getComment(commentId);
        return CommentDto.of(comment);
    }

    @Override
    public List<CommentDto> findAll() {
        return commentRepository.findAll()
            .stream()
            .map(CommentDto::of)
            .toList();
    }


    private Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
            .orElseThrow(() -> new InvalidIdException("댓글 저장소"));
    }
}
