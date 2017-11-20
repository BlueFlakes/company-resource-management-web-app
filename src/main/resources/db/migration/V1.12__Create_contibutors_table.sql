CREATE TABLE `contributors` (
  `contribution_id`	INTEGER NOT NULL,
  `student_id`	INTEGER,
  `coins`	INTEGER,
  FOREIGN KEY (`contribution_id`) REFERENCES `contributions`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`student_id`) REFERENCES `students`(`id`) ON DELETE CASCADE
);