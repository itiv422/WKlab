package wklab.Task1Rest.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import wklab.Task1Rest.domain.Teacher;
import wklab.Task1Rest.dto.TeacherDto;
import wklab.Task1Rest.service.iface.ITeacherService;

import javax.validation.Valid;

/**
 * Set of endpoints for Creating, Retrieving, Updating and Deleting of lesson.
 */
@RestController
@RequestMapping("teacher")
public class TeacherController {
    private final ITeacherService teacherService;

    /**
     * Teacher controller constructor.
     *
     * @param teacherService the teacher service.
     */
    public TeacherController(ITeacherService teacherService) {
        this.teacherService = teacherService;
    }

    /**
     * Returns all teachers.
     *
     * @param pageable the pageable.
     * @return all teachers are wrapped in pageable.
     */
    @GetMapping
    public Page<Teacher> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return teacherService.getAll(pageable);
    }

    /**
     * Returns teacher by id.
     *
     * @param id the teacher id.
     * @return teacher.
     */
    @GetMapping("{id}")
    public Teacher getById(@PathVariable("id") Long id) {
        return teacherService.getTeacherById(id);
    }

    /**
     * Returns busiest teacher.
     *
     * @return teacher.
     */
    @GetMapping("filter/busiestTeacher")
    public Teacher getBusiestTeacher() {
        return teacherService.getBusiestTeacher();
    }

    /**
     * Creates teacher.
     *
     * @param teacherDto the teacher dto.
     * @return teacher.
     */
    @PostMapping
    public Teacher create(@RequestBody @Valid TeacherDto teacherDto) {
        return teacherService.create(teacherDto);
    }

    /**
     * Updates teacher.
     *
     * @param id         the teacher id.
     * @param teacherDto the teacher dto.
     * @return teacher.
     */
    @PutMapping("{id}")
    public Teacher update(
            @PathVariable("id") Long id,
            @RequestBody @Valid TeacherDto teacherDto
    ) {
        return teacherService.update(id, teacherDto);
    }

    /**
     * Removes teacher.
     *
     * @param id the teacher id.
     */
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        teacherService.delete(id);
    }
}
