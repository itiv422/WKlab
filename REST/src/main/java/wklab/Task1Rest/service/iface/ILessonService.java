package wklab.Task1Rest.service.iface;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import wklab.Task1Rest.domain.Lesson;
import wklab.Task1Rest.dto.LessonDto;

public interface ILessonService {
    /**
     * Returns all lessons.
     *
     * @param pageable the pageable.
     * @return all lessons are wrapped in pageable.
     */
    Page<Lesson> getAll(Pageable pageable);

    /**
     * Returns lesson by id.
     *
     * @param id the lesson id.
     * @return lesson.
     */
    Lesson getLessonById(Long id);

    /**
     * Creates lesson.
     *
     * @param lessonDto the lesson dto.
     * @return lesson.
     */
    Lesson create(LessonDto lessonDto);

    /**
     * Updates lesson.
     *
     * @param id        the lesson id.
     * @param lessonDto the lesson dto.
     * @return product.
     */
    Lesson update(Long id, LessonDto lessonDto);

    /**
     * Removes lesson.
     *
     * @param id the lesson id.
     */
    void delete(Long id);
}
