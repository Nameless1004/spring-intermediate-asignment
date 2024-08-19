CREATE TABLE `schedule` (
	`id`	bigint	NOT NULL,
	`user_name`	varchar(20)	NOT NULL,
	`todo_title`	varchar(50)	NOT NULL,
	`todo_contents`	varchar(400)	NOT NULL,
	`created_date`	timestamp	NOT NULL,
	`updated_date`	timestamp	NOT NULL
);

CREATE TABLE `comment` (
	`id`	bigint	NOT NULL,
	`schedule_id`	bigint	NULL,
	`contents`	VARCHAR(255)	NOT NULL,
	`created_date`	timestamp	NOT NULL,
	`updated_date`	timestamp	NOT NULL,
	`writer_name`	VARCHAR(20)	NOT NULL
);

ALTER TABLE `schedule` ADD CONSTRAINT `PK_SCHEDULE` PRIMARY KEY (
	`id`
);

ALTER TABLE `comment` ADD CONSTRAINT `PK_COMMENT` PRIMARY KEY (
	`id`
);

