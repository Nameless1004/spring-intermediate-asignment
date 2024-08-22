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
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    /**
     * 댓글 등록
     */
    @Transactional(readOnly = false)
    public Long saveComment(CommentDto commentRequestDto) {
        Schedule schedule = scheduleRepository.findById(commentRequestDto.getScheduleId())
            .orElseThrow(
                () -> new InvalidIdException("일정 레포지토리", "일정", commentRequestDto.getScheduleId()));

        Comment comment = Comment.createComment(schedule, commentRequestDto);
        commentRepository.save(comment);
        return comment.getId();
    }

    /**
     * 댓글 업데이트
     */
    @Transactional(readOnly = false)
    public CommentDto updateComment(Long commentId, CommentUpdateDto commentRequestDto) {
        Comment comment = findCommentInRepository(commentId);

        comment.setContents(commentRequestDto.getContents());

        return CommentDto.createCommentDto(comment);
    }

    /**
     * 댓글 삭제
     */
    @Transactional(readOnly = false)
    public void deleteComment(Long commentId) {
        Comment comment = findCommentInRepository(commentId);
        commentRepository.delete(comment);
    }

    /**
     * 댓글 단건 조회
     */
    public CommentDto findOne(Long commentId) {
        Comment comment = findCommentInRepository(commentId);
        return CommentDto.createCommentDto(comment);
    }

    /**
     * 댓글 다건 조회
     */
    public List<CommentDto> findComments() {
        return commentRepository.findAll()
            .stream()
            .map(CommentDto::createCommentDto)
            .toList();
    }

    /**
     * CommentId가 유효한지 검사
     *
     * @return id가 존재하면 엔티티 반환
     * @throws InvalidIdException
     */
    private Comment findCommentInRepository(Long commentId) {
        return commentRepository.findById(commentId)
            .orElseThrow(() -> new InvalidIdException("댓글 레포지토리", "댓글", commentId));
    }
}
