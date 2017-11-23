CREATE TABLE `managers` (
  `id`	INTEGER PRIMARY KEY AUTOINCREMENT,
  `name`	TEXT,
  `login`	TEXT NOT NULL UNIQUE,
  `password`	TEXT NOT NULL,
  `email`	TEXT
);