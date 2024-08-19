package com.sparta.springintermediateasignment.comment.service;

import com.sparta.springintermediateasignment.comment.dto.CommentDto;
import com.sparta.springintermediateasignment.comment.entity.Comment;
import com.sparta.springintermediateasignment.comment.repository.CommentRepository;
import com.sparta.springintermediateasignment.schedule.entity.Schedule;
import com.sparta.springintermediateasignment.schedule.repository.ScheduleRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    @Transactional
    public Long save(Long scheduleId, CommentDto commentRequestDto) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일정 입니다."));

        commentRequestDto.setId(null);
        commentRequestDto.setCreatedAt(LocalDateTime.now());
        commentRequestDto.setUpdatedAt(LocalDateTime.now());
        commentRequestDto.setScheduleId(schedule.getId());

        Comment comment = Comment.of(schedule, commentRequestDto);
        commentRepository.save(comment);

        System.out.println("size = " + schedule.getComments()
            .size());
        return comment.getId();
    }

    @Override
    @Transactional
    public CommentDto update(Long commentId, CommentDto commentRequestDto) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        comment.setName(commentRequestDto.getWriterName());
        comment.setContents(commentRequestDto.getContents());
        comment.setUpdatedAt(LocalDateTime.now());

        commentRepository.save(comment);

        return CommentDto.of(comment);
    }

    @Override
    @Transactional
    public void delete(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        commentRepository.delete(comment);
    }

    @Override
    public CommentDto findById(Long id) {
        Comment comment = commentRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id입니다."));

        return CommentDto.of(comment);
    }

    @Override
    public List<CommentDto> findAll() {
        return commentRepository.findAll()
            .stream()
            .map(CommentDto::of)
            .toList();
    }
}
