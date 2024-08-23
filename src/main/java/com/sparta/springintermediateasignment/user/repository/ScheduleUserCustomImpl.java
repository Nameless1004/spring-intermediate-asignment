package com.sparta.springintermediateasignment.user.repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.springintermediateasignment.schedule.entity.QSchedule;
import com.sparta.springintermediateasignment.schedule.entity.Schedule;
import com.sparta.springintermediateasignment.user.entity.QScheduleUser;
import com.sparta.springintermediateasignment.user.entity.QUser;
import com.sparta.springintermediateasignment.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

public class ScheduleUserCustomImpl  implements ScheduleUserCustom {

    @Autowired
    JPAQueryFactory query;

    @Override
    public List<User> findUsersByScheduleId(Long scheduleId) {
        QScheduleUser su = QScheduleUser.scheduleUser;
        QSchedule s = QSchedule.schedule;
        QUser u = QUser.user;
        return query.select(u)
            .from(u)
            .leftJoin(su)
            .on(u.id.eq(su.userId))
            .where(su.scheduleId.eq(scheduleId))
            .fetchJoin()
            .fetch();
    }

    @Override
    public void deleteByScheduleId(Long scheduleId) {
        QScheduleUser su = QScheduleUser.scheduleUser;
        query.delete(su).where(su.scheduleId.eq(scheduleId)).execute();
    }

    @Override
    public void deleteByUserId(Long userId) {
        QScheduleUser su = QScheduleUser.scheduleUser;
        query.delete(su).where(su.userId.eq(userId)).execute();
    }
}
