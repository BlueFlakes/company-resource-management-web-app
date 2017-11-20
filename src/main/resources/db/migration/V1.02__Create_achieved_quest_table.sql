CREATE TABLE "achieved_quests" (
  `id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `name` TEXT, `description` TEXT,
  `value` INTEGER NOT NULL,
  `owner_id` INTEGER NOT NULL,
  `date` TEXT,
  `category_id` INTEGER NOT NULL,
  FOREIGN KEY(`category_id`) REFERENCES `quest_categories`(`id`) ON DELETE CASCADE )