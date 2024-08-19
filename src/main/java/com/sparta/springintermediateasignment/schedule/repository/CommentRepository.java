package com.sparta.springintermediateasignment.schedule.repository;

import com.sparta.springintermediateasignment.schedule.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
