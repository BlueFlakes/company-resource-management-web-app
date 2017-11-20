CREATE TABLE `bought_artifacts` (
  `id`	INTEGER PRIMARY KEY AUTOINCREMENT,
  `name`	TEXT,
  `price`	INTEGER,
  `category_id`	INTEGER NOT NULL,
  `purchase_date`	TEXT,
  `is_used`	INTEGER NOT NULL,
  `description`	TEXT,
  FOREIGN KEY(`category_id`) REFERENCES `artifact_categories`(`id`) ON DELETE CASCADE
);