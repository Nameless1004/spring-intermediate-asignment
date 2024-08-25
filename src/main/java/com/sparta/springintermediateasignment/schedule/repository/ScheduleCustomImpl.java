package com.sparta.springintermediateasignment.schedule.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.springintermediateasignment.comment.entity.Comment;
import com.sparta.springintermediateasignment.comment.entity.QComment;
import com.sparta.springintermediateasignment.schedule.entity.QSchedule;
import com.sparta.springintermediateasignment.schedule.entity.Schedule;
import com.sparta.springintermediateasignment.user.entity.QUser;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

public class ScheduleCustomImpl implements ScheduleCustom {

    @Autowired
    JPAQueryFactory queryFactory;

    @Override
    public List<Comment> findCommentsByScheduleId(Long scheduleId) {
//        QComment comment = QComment.comment;
//        QSchedule schedule = QSchedule.schedule;
//
//        List<Tuple> fetch = queryFactory.select(comment.id, comment.schedule.id.as("schedule_id"),
//                comment.name, comment.contents)
//            .from(comment)
//            .join(schedule)
//            .on(comment.scheduleId.eq(schedule.id))
//            .where(comment.scheduleId.eq(scheduleId))
//            .orderBy(comment.updatedDate.desc())
//            .fetchJoin()// 레이지 로딩 되지 않게
//            .fetch();
//
//        return fetch.stream().map(tupple-> Comment.builder().
//            id(tupple.get(comment.id))
//            .scheduleId(tupple.get(comment.scheduleId))
//            .name(tupple.get(comment.name))
//            .contents(tupple.get(comment.contents))
//            .build()).toList();
        return null;
    }
}
