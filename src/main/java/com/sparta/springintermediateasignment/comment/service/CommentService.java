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
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    /**
     * 댓글 등록
     */
    public CommentDto saveComment(Long scheduleId, CommentDto commentRequestDto) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
            .orElseThrow(
                () -> new InvalidIdException("일정 레포지토리", "일정", commentRequestDto.getScheduleId()));

        Comment comment = Comment.createComment(schedule, commentRequestDto);
        Comment save = commentRepository.save(comment);
        return CommentDto.createCommentDto(save);
    }

    /**
     * 댓글 업데이트
     */
    public CommentDto updateComment(Long scheduleId, Long commentId,
        CommentUpdateDto commentRequestDto) {
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        comment.update(commentRequestDto.getContents());

        return CommentDto.createCommentDto(comment);
    }

    /**
     * 댓글 삭제
     */
    public void deleteComment(Long scheduleId, Long commentId) {
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);
        if (!comment.getSchedule()
            .getId()
            .equals(scheduleId)) {
            throw new IllegalArgumentException("해당 일정에 포함되지 않은 댓글입니다.");
        }

        commentRepository.delete(comment);
    }

    /**
     * 댓글 단건 조회
     */
    @Transactional(readOnly = true)
    public CommentDto findOne(Long scheduleId, Long commentId) {
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        if (!comment.getSchedule()
            .getId()
            .equals(scheduleId)) {
            throw new IllegalArgumentException("해당 일정에 포함되지 않은 댓글입니다.");
        }

        return CommentDto.createCommentDto(comment);
    }

    /**
     * 댓글 다건 조회
     */
    @Transactional(readOnly = true)
    public List<CommentDto> findComments(Long scheduleId) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);
        List<Comment> comments = schedule.getComments();

        return comments
            .stream()
            .map(CommentDto::createCommentDto)
            .toList();
    }

}
