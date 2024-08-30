package com.sparta.springintermediateasignment.comment.repository;

import com.sparta.springintermediateasignment.comment.entity.Comment;
import com.sparta.springintermediateasignment.common.exceptoins.InvalidIdException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentCustom {

    /**
     * CommentId가 유효한지 검사
     *
     * @return CommentId가 존재하면 엔티티 반환
     * @throws IllegalArgumentException
     */
    default Comment findByIdOrElseThrow(Long commentId) {
        return findById(commentId)
            .orElseThrow(() -> new InvalidIdException("댓글 레포지토리", "댓글", commentId));
    }
}
