package com.sparta.springintermediateasignment.comment.repository;

import com.sparta.springintermediateasignment.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
