package com.sparta.springintermediateasignment.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QScheduleUser is a Querydsl query type for ScheduleUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QScheduleUser extends EntityPathBase<ScheduleUser> {

    private static final long serialVersionUID = -1303975096L;

    public static final QScheduleUser scheduleUser = new QScheduleUser("scheduleUser");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> scheduleId = createNumber("scheduleId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QScheduleUser(String variable) {
        super(ScheduleUser.class, forVariable(variable));
    }

    public QScheduleUser(Path<? extends ScheduleUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QScheduleUser(PathMetadata metadata) {
        super(ScheduleUser.class, metadata);
    }

}

