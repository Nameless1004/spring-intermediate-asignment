package com.sparta.springintermediateasignment.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.springintermediateasignment.comment.entity.Comment;
import com.sparta.springintermediateasignment.comment.entity.QComment;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentCustomImpl implements CommentCustom {

    @Autowired
    JPAQueryFactory query;

    @Override
    public List<Comment> findByScheduleId(Long scheduleId) {
        QComment c = QComment.comment;
        List<Comment> result = query.select(c)
            .from(c)
            .where(c.schedule.id.eq(scheduleId))
            .fetch();
        return result;
    }
}
