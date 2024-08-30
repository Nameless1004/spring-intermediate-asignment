package com.sparta.springintermediateasignment.comment.controller;

import com.sparta.springintermediateasignment.comment.dto.CommentDto;
import com.sparta.springintermediateasignment.comment.dto.CommentUpdateDto;
import com.sparta.springintermediateasignment.comment.service.CommentService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
// 수정
@RequestMapping("/api")
@Validated
public class CommentController {

    private final CommentService service;

    @PostMapping("/schedules/{scheduleId}/comments/")
    public ResponseEntity<CommentDto> postScheduleComment(
        @PathVariable("scheduleId") Long scheduleId,
        @Valid @RequestBody CommentDto commentDto) {
        CommentDto dto = service.saveComment(scheduleId, commentDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(dto);
    }

    @PatchMapping("/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity<CommentDto> patchScheduleComment(
        @PathVariable("scheduleId") Long scheduleId,
        @PathVariable("commentId") Long commentId,
        @Valid @RequestBody CommentUpdateDto commentDto) {
        return ResponseEntity.ok(service.updateComment(scheduleId, commentId, commentDto));
    }

    // 다건조회
    @GetMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<List<CommentDto>> getScheduleComments(
        @PathVariable("scheduleId") Long scheduleId) {
        return ResponseEntity.ok(service.findComments(scheduleId));
    }

    // 단건조회
    @GetMapping("/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getComment(
        @PathVariable("scheduleId") Long scheduleId,
        @PathVariable("commentId") Long commentId) {
        return ResponseEntity.ok(service.findOne(scheduleId, commentId));
    }

    @DeleteMapping("/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
        @PathVariable("scheduleId") Long scheduleId,
        @PathVariable("commentId") Long commentId) {
        service.deleteComment(scheduleId, commentId);
        return ResponseEntity.ok()
            .build();
    }
}
