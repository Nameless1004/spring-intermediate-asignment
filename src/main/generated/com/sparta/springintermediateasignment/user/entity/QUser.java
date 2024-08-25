package com.sparta.springintermediateasignment.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;

/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -228348399L;

    public static final QUser user = new QUser("user");

    public final com.sparta.springintermediateasignment.common.QBaseTimeEntity _super = new com.sparta.springintermediateasignment.common.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final ListPath<com.sparta.springintermediateasignment.schedule.entity.Schedule, com.sparta.springintermediateasignment.schedule.entity.QSchedule> createdSchedules = this.<com.sparta.springintermediateasignment.schedule.entity.Schedule, com.sparta.springintermediateasignment.schedule.entity.QSchedule>createList("createdSchedules", com.sparta.springintermediateasignment.schedule.entity.Schedule.class, com.sparta.springintermediateasignment.schedule.entity.QSchedule.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<ScheduleUser, QScheduleUser> managedSchedules = this.<ScheduleUser, QScheduleUser>createList("managedSchedules", ScheduleUser.class, QScheduleUser.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final EnumPath<com.sparta.springintermediateasignment.user.enums.UserRole> role = createEnum("role", com.sparta.springintermediateasignment.user.enums.UserRole.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

