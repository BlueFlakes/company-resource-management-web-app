INSERT INTO contributions (id,contribution_name,creator_id,artifact_id,given_coins,status) VALUES (
3,'Zrzuta na buty dla matiego',1,8,900,'open');
INSERT INTO contributions (id,contribution_name,creator_id,artifact_id,given_coins,status) VALUES (
4,'Ambergold version 2',1,7,720,'open');

INSERT INTO shop_artifacts (id,name,price,category_id,description) VALUES (
2,'Sanctuary',300,1,'You can spend a day in home office');

INSERT INTO students (id,name,login,password,email,class_id,earned_coins,possesed_coins) VALUES (
4,'Kamil Konior','kucyk','mpyd','konior@gmail.com',2,850,400);
INSERT INTO students (id,name,login,password,email,class_id,earned_coins,possesed_coins) VALUES (
5,'Jakub Janiszewski','wisniewski','hofzeypl','j.janiszewski@gmail.com',2,550,420);
INSERT INTO students (id,name,login,password,email,class_id,earned_coins,possesed_coins) VALUES (
6,'Rafał Kordas','kordek','qnaf','kordas@gmail.com',4,150,50);
INSERT INTO students (id,name,login,password,email,class_id,earned_coins,possesed_coins) VALUES (
7,'Monika Zaganiacz','monia','mngepjploa','m.zaganiacz@gmail.com',2,850,50);
INSERT INTO students (id,name,login,password,email,class_id,earned_coins,possesed_coins) VALUES (
8,'Gracjan Cisakowski','gracek','gls','LysyZBrazzers@gmail.com',2,350,150);
INSERT INTO students (id,name,login,password,email,class_id,earned_coins,possesed_coins) VALUES (
9,'Barbara Pilch','basja','mlhhjfag123','b.pilch@gmail.com',4,150,50);
INSERT INTO students (id,name,login,password,email,class_id,earned_coins,possesed_coins) VALUES (
10,'Marta Sajdak','mrt','dlgwlh','sajdak@gmail.com',1,1050,350);

INSERT INTO mentors (id,name,login,password,email,class_id) VALUES (
6,'Dominik Starzyk','domi','fkg','starzyk@codecool.pl',3);
INSERT INTO mentors (id,name,login,password,email,class_id) VALUES (
7,'Konrad Gadzina','plaz','slklklwdl','k.gadzina@codecool.pl',3);
INSERT INTO mentors (id,name,login,password,email,class_id) VALUES (
8,'Rafał Stępień','rafi','nrsnggng','stepien@codecool.pl',1);
INSERT INTO mentors (id,name,login,password,email,class_id) VALUES (
9,'Piotr Tomaszewski','pito','lgnmo','tomaszewski@codecool.pl',3);

INSERT INTO classes (id,name) VALUES (
4,'codecool-krk-2017-3');
