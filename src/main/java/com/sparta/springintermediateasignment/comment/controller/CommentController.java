package com.sparta.springintermediateasignment.comment.controller;

import com.sparta.springintermediateasignment.comment.dto.CommentDto;
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
@RequestMapping("/api/comments")
@Validated
public class CommentController {
    private final CommentService service;

    @PostMapping
    public Long postScheduleComment(@Valid @RequestBody CommentDto commentDto, @RequestParam Long scheduleId) {
        return service.save(scheduleId, commentDto);
    }

    @PatchMapping("/{id}")
    public CommentDto patchScheduleComment(@PathVariable Long id, @Valid @RequestBody CommentDto commentDto) {
        return service.update(id, commentDto);
    }


    @GetMapping
    public List<CommentDto> getScheduleComments() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public CommentDto getComment(@PathVariable Long id){
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
