delete from person;
delete from friends_xsd;
insert into person (id , day, month, year, name) values (1, 1, 1, 1988, 'Jon');
insert into person (id , day, month, year, name) values (2, 1, 1, 1989, 'Kate');
insert into person (id , day, month, year, name) values (3, 1, 1, 1990, 'Diana');
insert into person (id , day, month, year, name) values (4, 1, 1, 1991, 'Yana');
insert into person (id , day, month, year, name) values (5, 1, 1, 1991, 'Max');
insert into friends_xsd (person_id, friend_id) values (1, 2);
insert into friends_xsd (person_id, friend_id) values (1, 3);
insert into friends_xsd (person_id, friend_id) values (1, 4);
insert into friends_xsd (person_id, friend_id) values (1, 5);
insert into friends_xsd (person_id, friend_id) values (2, 4);
insert into friends_xsd (person_id, friend_id) values (2, 5);
insert into friends_xsd (person_id, friend_id) values (4, 5);
alter sequence hibernate_sequence restart with 6;
