package com.sparta.springintermediateasignment.schedule.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSchedule is a Querydsl query type for Schedule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSchedule extends EntityPathBase<Schedule> {

    private static final long serialVersionUID = 53170601L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSchedule schedule = new QSchedule("schedule");

    public final com.sparta.springintermediateasignment.common.QBaseTimeEntity _super = new com.sparta.springintermediateasignment.common.QBaseTimeEntity(this);

    public final com.sparta.springintermediateasignment.user.entity.QUser author;

    public final ListPath<com.sparta.springintermediateasignment.comment.entity.Comment, com.sparta.springintermediateasignment.comment.entity.QComment> comments = this.<com.sparta.springintermediateasignment.comment.entity.Comment, com.sparta.springintermediateasignment.comment.entity.QComment>createList("comments", com.sparta.springintermediateasignment.comment.entity.Comment.class, com.sparta.springintermediateasignment.comment.entity.QComment.class, PathInits.DIRECT2);
    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.sparta.springintermediateasignment.user.entity.ScheduleUser, com.sparta.springintermediateasignment.user.entity.QScheduleUser> managedUser = this.<com.sparta.springintermediateasignment.user.entity.ScheduleUser, com.sparta.springintermediateasignment.user.entity.QScheduleUser>createList("managedUser", com.sparta.springintermediateasignment.user.entity.ScheduleUser.class, com.sparta.springintermediateasignment.user.entity.QScheduleUser.class, PathInits.DIRECT2);

    public final StringPath todoContents = createString("todoContents");

    public final StringPath todoTitle = createString("todoTitle");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final StringPath weather = createString("weather");

    public QSchedule(String variable) {
        this(Schedule.class, forVariable(variable), INITS);
    }

    public QSchedule(Path<? extends Schedule> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSchedule(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSchedule(PathMetadata metadata, PathInits inits) {
        this(Schedule.class, metadata, inits);
    }

    public QSchedule(Class<? extends Schedule> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new com.sparta.springintermediateasignment.user.entity.QUser(forProperty("author")) : null;
    }

}

