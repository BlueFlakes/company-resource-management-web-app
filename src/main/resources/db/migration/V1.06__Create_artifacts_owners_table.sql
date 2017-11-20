CREATE TABLE "artifact_owners" (
  `artifact_id` INTEGER NOT NULL,
  `student_id` INTEGER NOT NULL,
  FOREIGN KEY(`artifact_id`) REFERENCES `bought_artifacts`(`id`) ON DELETE CASCADE,
  FOREIGN KEY(`student_id`) REFERENCES `students`(`id`) ON DELETE CASCADE )