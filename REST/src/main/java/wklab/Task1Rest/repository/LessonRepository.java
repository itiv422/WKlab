package wklab.Task1Rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wklab.Task1Rest.domain.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
