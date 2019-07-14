package wklab.Task1Rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wklab.Task1Rest.domain.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query(value = "select * from teacher INNER JOIN (select teacher_id, SUM(duration) as busiest from lesson group by teacher_id order by busiest desc limit 1) as t1 where id = t1.teacher_id",nativeQuery = true)
    Teacher getBusiestTeacher();
}
