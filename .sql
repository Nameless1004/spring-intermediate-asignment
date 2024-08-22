CREATE TABLE `schedule` (
                            `id`	bigint	NOT NULL,
                            `user_id`	bigint	NOT NULL,
                            `todo_title`	varchar(50)	NOT NULL,
                            `todo_contents`	varchar(255)	NOT NULL,
                            `created_date`	timestamp	NOT NULL,
                            `updated_date`	timestamp	NOT NULL
);

CREATE TABLE `comment` (
                           `id`	bigint	NOT NULL,
                           `schedule_id`	bigint	NULL,
                           `author_name`	varchar(50)	NOT NULL,
                           `contents`	varchar(255)	NOT NULL,
                           `created_date`	timestamp	NOT NULL,
                           `updated_date`	timestamp	NOT NULL
);

CREATE TABLE `user` (
                        `user_id`	bigint	NOT NULL,
                        `user_name`	varchar(30)	NOT NULL,
                        `user_password`	varchar(30)	NOT NULL,
                        `user_email`	varchar(50)	NOT NULL,
                        `user_role`	varchar(50)	NOT NULL
);

CREATE TABLE `schedule_manager` (
                                    `id`	bigint	NOT NULL,
                                    `schedule_id`	bigint	NOT NULL,
                                    `user_id`	bigint	NOT NULL
);

ALTER TABLE `schedule` ADD CONSTRAINT `PK_SCHEDULE` PRIMARY KEY (
                                                                 `id`
    );

ALTER TABLE `comment` ADD CONSTRAINT `PK_COMMENT` PRIMARY KEY (
                                                               `id`
    );

ALTER TABLE `user` ADD CONSTRAINT `PK_USER` PRIMARY KEY (
                                                         `user_id`
    );

ALTER TABLE `schedule_manager` ADD CONSTRAINT `PK_SCHEDULE_MANAGER` PRIMARY KEY (
                                                                                 `id`
    );

ALTER TABLE `schedule` ADD CONSTRAINT `FK_user_TO_schedule_1` FOREIGN KEY (
                                                                           `user_id`
    )
    REFERENCES `user` (
                       `user_id`
        );

ALTER TABLE `comment` ADD CONSTRAINT `FK_schedule_TO_comment_1` FOREIGN KEY (
                                                                             `schedule_id`
    )
    REFERENCES `schedule` (
                           `id`
        );

ALTER TABLE `schedule_manager` ADD CONSTRAINT `FK_schedule_TO_schedule_manager_1` FOREIGN KEY (
                                                                                               `schedule_id`
    )
    REFERENCES `schedule` (
                           `id`
        );

ALTER TABLE `schedule_manager` ADD CONSTRAINT `FK_user_TO_schedule_manager_1` FOREIGN KEY (
                                                                                           `user_id`
    )
    REFERENCES `user` (
                       `user_id`
        );

