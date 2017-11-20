CREATE TABLE `contributions` (
  `id`	INTEGER PRIMARY KEY AUTOINCREMENT,
  `contribution_name`	TEXT,
  `creator_id`	INTEGER,
  `artifact_id`	INTEGER,
  `given_coins`	INTEGER,
  `status`	TEXT,
  FOREIGN KEY (`creator_id`) REFERENCES `students`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`artifact_id`) REFERENCES `shop_artifacts`(`id`) ON DELETE CASCADE
);