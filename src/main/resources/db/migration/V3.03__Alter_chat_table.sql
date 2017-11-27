DROP TABLE `chat`;
CREATE TABLE `chat` (
  `id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `name` TEXT,
  `message` TEXT,
  `room_id` INTEGER,
  FOREIGN KEY (`room_id`) REFERENCES `chat_rooms`(`id`) ON DELETE CASCADE
);