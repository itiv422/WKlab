package wklab.Task1Rest.service.iface;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import wklab.Task1Rest.domain.Teacher;
import wklab.Task1Rest.dto.TeacherDto;

public interface ITeacherService {

    /**
     * Returns all teachers.
     *
     * @param pageable the pageable.
     * @return all teachers are wrapped in pageable.
     */
    Page<Teacher> getAll(Pageable pageable);

    /**
     * Returns teacher by id.
     *
     * @param id the teacher id.
     * @return teacher.
     */
    Teacher getTeacherById(Long id);

    /**
     * Creates teacher.
     *
     * @param teacherDto the teacher dto.
     * @return teacher.
     */
    Teacher create(TeacherDto teacherDto);

    /**
     * Updates teacher.
     *
     * @param id         the teacher id.
     * @param teacherDto the teacher dto.
     * @return teacher.
     */
    Teacher update(Long id, TeacherDto teacherDto);

    /**
     * Removes teacher.
     *
     * @param id the teacher id.
     */
    void delete(Long id);

    /**
     * Returns busiest teacher.
     *
     * @return teacher.
     */
    Teacher getBusiestTeacher();
}
