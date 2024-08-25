package com.sparta.springintermediateasignment.user.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.util.CollectionUtils;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleManagerInfoDto;
import com.sparta.springintermediateasignment.schedule.entity.QSchedule;
import com.sparta.springintermediateasignment.schedule.entity.Schedule;
import com.sparta.springintermediateasignment.user.entity.QScheduleUser;
import com.sparta.springintermediateasignment.user.entity.QUser;
import com.sparta.springintermediateasignment.user.entity.ScheduleUser;
import com.sparta.springintermediateasignment.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

public class ScheduleUserCustomImpl  implements ScheduleUserCustom {

    @Autowired
    JPAQueryFactory query;

    @Override
    public List<ScheduleManagerInfoDto> findUsersByScheduleId(Long scheduleId) {
        QScheduleUser su = QScheduleUser.scheduleUser;
        QUser u = QUser.user;
        List<Long> ids = query.select(su.user.id)
            .from(su)
            .where(su.schedule.id.eq(scheduleId)).fetch();
        if(ids.isEmpty()){
            return new ArrayList<>();
        }

        return query.select(Projections.fields(ScheduleManagerInfoDto.class,
            u.id,
            u.name,
            u.email
            )).from(u)
            .where(u.id.in(ids))
            .fetch();
    }

    @Override
    public Optional<ScheduleUser> findByUserIdAndScheduleId(Long userId, Long scheduleId) {
        QScheduleUser su = QScheduleUser.scheduleUser;
        ScheduleUser scheduleUser = query.select(su)
            .from(su)
            .where(su.user.id.eq(userId).and(su.schedule.id.eq(scheduleId)))
            .fetchOne();
        if(scheduleUser == null) return Optional.empty();
        return Optional.of(scheduleUser);
    }
}
