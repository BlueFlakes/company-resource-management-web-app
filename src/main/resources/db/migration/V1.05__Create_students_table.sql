CREATE TABLE `students` (
  `id`	INTEGER PRIMARY KEY AUTOINCREMENT,
  `name`	TEXT,
  `login`	TEXT NOT NULL UNIQUE,
  `password`	TEXT NOT NULL,
  `email`	TEXT,
  `class_id`	INTEGER NOT NULL,
  `earned_coins`	INTEGER,
  `possesed_coins`	INTEGER,
  FOREIGN KEY(`class_id`) REFERENCES `classes`(`id`) ON DELETE CASCADE
);