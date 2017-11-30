ALTER TABLE `available_quests`
ADD	`max_value`	TEXT DEFAULT '0';

UPDATE `available_quests`
SET `value` = 500, `max_value` = 1000
WHERE `name` = "Achiever";