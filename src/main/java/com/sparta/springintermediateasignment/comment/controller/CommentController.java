package com.sparta.springintermediateasignment.comment.controller;

import com.sparta.springintermediateasignment.comment.dto.CommentDto;
import com.sparta.springintermediateasignment.comment.dto.CommentUpdateDto;
import com.sparta.springintermediateasignment.comment.service.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
// 수정
@RequestMapping("/api/schedules/comments")
@Validated
public class CommentController {
    private final CommentService service;

    @PostMapping
    public ResponseEntity<CommentDto> postScheduleComment(@Valid @RequestBody CommentDto commentDto) {
        Long id = service.save(commentDto);
        CommentDto dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommentDto> patchScheduleComment(@PathVariable Long id, @Valid @RequestBody CommentUpdateDto commentDto) {
        return ResponseEntity.ok(service.update(id, commentDto));
    }

    // 다건조회
    @GetMapping
    public ResponseEntity<List<CommentDto>> getScheduleComments() {
        return ResponseEntity.ok(service.findAll());
    }

    // 단건조회
    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getComment(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
