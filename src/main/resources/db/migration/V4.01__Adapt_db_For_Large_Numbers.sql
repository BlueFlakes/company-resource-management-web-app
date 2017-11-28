DROP TABLE `students`;
CREATE TABLE `students` (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT,
	`login`	TEXT NOT NULL UNIQUE,
	`password`	TEXT NOT NULL,
	`email`	TEXT,
	`class_id`	INTEGER NOT NULL,
	`earned_coins`	TEXT,
	`possesed_coins`	TEXT,
	FOREIGN KEY(`class_id`) REFERENCES `classes`(`id`) ON DELETE CASCADE
);
DROP TABLE `shop_artifacts`;
CREATE TABLE `shop_artifacts` (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT,
	`price`	TEXT NOT NULL,
	`category_id`	INTEGER NOT NULL,
	`description`	TEXT,
	FOREIGN KEY(`category_id`) REFERENCES `artifact_categories`(`id`) ON DELETE CASCADE
);
DROP TABLE `experience_levels`;
CREATE TABLE `experience_levels` (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`coins_needed`	TEXT
);
DROP TABLE `contributors`;
CREATE TABLE `contributors` (
	`contribution_id`	INTEGER NOT NULL,
	`student_id`	INTEGER,
	`coins`	TEXT,
	FOREIGN KEY(`student_id`) REFERENCES `students`(`id`) ON DELETE CASCADE,
	FOREIGN KEY(`contribution_id`) REFERENCES `contributions`(`id`) ON DELETE CASCADE
);
DROP TABLE `contributions`;
CREATE TABLE `contributions` (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`contribution_name`	TEXT,
	`creator_id`	INTEGER,
	`artifact_id`	INTEGER,
	`given_coins`	TEXT,
	`status`	TEXT,
	FOREIGN KEY(`creator_id`) REFERENCES `students`(`id`) ON DELETE CASCADE,
	FOREIGN KEY(`artifact_id`) REFERENCES `shop_artifacts`(`id`) ON DELETE CASCADE
);
DROP TABLE `bought_artifacts`;
CREATE TABLE `bought_artifacts` (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT,
	`price`	TEXT,
	`category_id`	INTEGER NOT NULL,
	`purchase_date`	TEXT,
	`is_used`	INTEGER NOT NULL,
	`description`	TEXT,
	FOREIGN KEY(`category_id`) REFERENCES `artifact_categories`(`id`) ON DELETE CASCADE
);
DROP TABLE `available_quests`;
CREATE TABLE `available_quests` (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT,
	`description`	TEXT,
	`value`	TEXT NOT NULL,
	`category_id`	INTEGER NOT NULL,
	FOREIGN KEY(`category_id`) REFERENCES `quest_categories`(`id`) ON DELETE CASCADE
);
DROP TABLE `achieved_quests`;
CREATE TABLE `achieved_quests` (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT,
	`description`	TEXT,
	`value`	TEXT NOT NULL,
	`owner_id`	INTEGER NOT NULL,
	`date`	TEXT,
	`category_id`	INTEGER NOT NULL,
	FOREIGN KEY(`category_id`) REFERENCES `quest_categories`(`id`) ON DELETE CASCADE
);
