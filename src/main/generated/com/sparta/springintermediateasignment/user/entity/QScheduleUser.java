package com.sparta.springintermediateasignment.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QScheduleUser is a Querydsl query type for ScheduleUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QScheduleUser extends EntityPathBase<ScheduleUser> {

    private static final long serialVersionUID = -1303975096L;

    private static final PathInits INITS = PathInits.DIRECT2;
    public static final QScheduleUser scheduleUser = new QScheduleUser("scheduleUser");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.sparta.springintermediateasignment.schedule.entity.QSchedule schedule;

    public final QUser user;

    public QScheduleUser(String variable) {
        this(ScheduleUser.class, forVariable(variable), INITS);
    }

    public QScheduleUser(Path<? extends ScheduleUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QScheduleUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QScheduleUser(PathMetadata metadata, PathInits inits) {
        this(ScheduleUser.class, metadata, inits);
    }

    public QScheduleUser(Class<? extends ScheduleUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.schedule = inits.isInitialized("schedule") ? new com.sparta.springintermediateasignment.schedule.entity.QSchedule(forProperty("schedule"), inits.get("schedule")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

