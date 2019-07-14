delete from lesson;
delete from teacher;
insert into teacher (id , day, month, year, name) values (1, 1, 1, 1988, 'Jon'), (2, 2, 2, 1999, 'Kate'), (3, 3, 3, 1977, 'Mira');
insert into lesson (id, duration, name, teacher_id) values (4, 45, 'math', 1), (5, 45, 'physics', 1), (6, 100, 'math', 2), (7, 60, 'math', 3), (8, 60, 'physics', 3);
