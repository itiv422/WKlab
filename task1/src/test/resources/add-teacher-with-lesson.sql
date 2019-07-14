delete from lesson;
delete from teacher;
insert into teacher (id , day, month, year, name) values (1, 10, 4, 1988, 'Jon');
insert into lesson (id, duration, name, teacher_id) values (2, 45, 'math', 1);
alter sequence hibernate_sequence restart with 3;
