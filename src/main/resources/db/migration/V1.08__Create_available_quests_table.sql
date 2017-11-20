CREATE TABLE `available_quests` (
  `id`	INTEGER PRIMARY KEY AUTOINCREMENT,
  `name`	TEXT,
  `description`	TEXT,
  `value`	INTEGER NOT NULL,
  `category_id`	INTEGER NOT NULL,
  FOREIGN KEY(`category_id`) REFERENCES `quest_categories`(`id`) ON DELETE CASCADE
);