package com.sparta.springintermediateasignment.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.springintermediateasignment.comment.entity.Comment;
import com.sparta.springintermediateasignment.comment.entity.QComment;
import com.sparta.springintermediateasignment.schedule.entity.QSchedule;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentCustomImpl implements CommentCustom {

    @Autowired  JPAQueryFactory query;

    @Override
    public List<Comment> findByScheduleId(Long scheduleId) {
        QComment c = QComment.comment;
        List<Comment> result = query.select(c)
            .from(c)
            .where(c.scheduleId.eq(scheduleId))
            .fetch();
        return result;
    }

    @Override
    public void deleteAllByScheduleId(Long scheduleId) {
        QComment c = QComment.comment;
        query.delete(c)
            .where(c.scheduleId.eq(scheduleId)).execute();
    }
}
