CREATE TABLE `shop_artifacts` (
  `id`	INTEGER PRIMARY KEY AUTOINCREMENT,
  `name`	TEXT,
  `price`	INTEGER NOT NULL,
  `category_id`	INTEGER NOT NULL,
  `description`	TEXT,
  FOREIGN KEY(`category_id`) REFERENCES `artifact_categories`(`id`) ON DELETE CASCADE
);