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
@RequestMapping("/api/schedules/comments")
@Validated
public class CommentController {

    private final CommentService service;

    @PostMapping
    public ResponseEntity<CommentDto> postScheduleComment(
        @Valid @RequestBody CommentDto commentDto) {
        Long id = service.saveComment(commentDto);
        CommentDto dto = service.findOne(id);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(dto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommentDto> patchScheduleComment(@PathVariable("id") Long id,
        @Valid @RequestBody CommentUpdateDto commentDto) {
        return ResponseEntity.ok(service.updateComment(id, commentDto));
    }

    // 다건조회
    @GetMapping
    public ResponseEntity<List<CommentDto>> getScheduleComments() {
        return ResponseEntity.ok(service.findComments());
    }

    // 단건조회
    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getComment(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") Long id) {
        service.deleteComment(id);
        return ResponseEntity.ok()
            .build();
    }
}
