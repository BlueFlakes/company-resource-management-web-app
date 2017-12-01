CREATE TABLE `mentors` (
  `id`	INTEGER PRIMARY KEY AUTOINCREMENT,
  `name`	TEXT,
  `login`	TEXT NOT NULL UNIQUE,
  `password`	TEXT NOT NULL,
  `email`	TEXT,
  `class_id`	INTEGER NOT NULL,
  FOREIGN KEY(`class_id`) REFERENCES `classes`(`id`) ON DELETE CASCADE
);