INSERT INTO students
VALUES (1,'Maciej Nowak','mcnowak','boczniak','mcnowak@wp.pl',2,420,320),
(2,'Jakub Skiba','hey','jude','skibus@gmail.com',2,800,520),
(3,'Paweł Polakiewicz','grande','cycle','polak@gmail.com',1,250,100);

INSERT INTO shop_artifacts
VALUES (1,'Combat training',50,1,'Private mentoring'),
(2,'Sanctuary',300,1,'You can spend a day in home office'),
(3,'Time Travel',500,1,'extend SI week assignment deadline by one day'),
(4,'Circle of Sorcery',1000,2,'60 min workshop by a mentor(s) of the chosen topic'),
(5,'Summon Code Elemental',1000,2,'mentor joins a students'' team for a one hour'),
(6,'Tome of knowledge',500,2,'Extra material for the current topic'),
(7,'Transform mentors',5000,2,'All mentors should dress up as pirates (or just funny) for the day'),
(8,'Teleport',30000,2,'The whole course goes to an off-school program instead for a day');

INSERT INTO quest_categories
VALUES (1,'Basic Quests'),
(2,'Extra Quests');

INSERT INTO mentors
VALUES (1,'Mateusz Ostafil','gestapo','Berlin','ostafil@codecool.pl',2),
(2,'Agnieszka Koszany','kaczuszka','yellow','koszany@codecool.pl',2),
(3,'Mateusz Steliga','scooby','doo','steliga@codecool.pl',2),
(4,'Przemysław Ciąćka','przemo','paczuszka','ciacka@codecool.pl',1),
(5,'Marcin Izworski','sharp','beef','marcin@codecool.pl',1);

INSERT INTO managers
VALUES (1,'Jerzy Mardaus','myfriend','hungary','jerzy@codecool.pl'),
(2,'Creepy Guy','vlad','tepes','draco@codecool.com');

INSERT INTO classes VALUES (1,'codecool-krk-2016-1'),
(2,'codecool-krk-2017-1'),
(3,'codecool-krk-2017-2');

INSERT INTO artifact_categories
VALUES (1,'Basic items'),
(2,'Magic items');
