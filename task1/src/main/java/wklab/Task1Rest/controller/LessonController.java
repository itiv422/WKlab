package wklab.Task1Rest.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import wklab.Task1Rest.domain.Lesson;
import wklab.Task1Rest.dto.LessonDto;
import wklab.Task1Rest.service.iface.ILessonService;

import javax.validation.Valid;

/**
 * Set of endpoints for Creating, Retrieving, Updating and Deleting of lesson.
 */
@RestController
@RequestMapping("lesson")
public class LessonController {

    private final ILessonService lessonService;

    /**
     * Price controller constructor.
     *
     * @param lessonService the lesson service.
     */
    public LessonController(ILessonService lessonService) {
        this.lessonService = lessonService;
    }

    /**
     * Returns all lessons.
     *
     * @param pageable the pageable.
     * @return all lessons are wrapped in pageable.
     */
    @GetMapping
    public Page<Lesson> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return lessonService.getAll(pageable);
    }

    /**
     * Returns lesson by id.
     *
     * @param id the lesson id.
     * @return lesson.
     */
    @GetMapping("{id}")
    public Lesson getById(@PathVariable("id") Long id) {
        return lessonService.getLessonById(id);
    }

    /**
     * Creates lesson.
     *
     * @param lessonDto the lesson dto.
     * @return lesson.
     */
    @PostMapping
    public Lesson create(@RequestBody @Valid LessonDto lessonDto) {
        return lessonService.create(lessonDto);
    }

    /**
     * Updates lesson.
     *
     * @param id        the lesson id.
     * @param lessonDto the lesson dto.
     * @return product.
     */
    @PutMapping("{id}")
    public Lesson update(
            @PathVariable("id") Long id,
            @RequestBody @Valid LessonDto lessonDto
    ) {
        return lessonService.update(id, lessonDto);
    }

    /**
     * Removes lesson.
     *
     * @param id the lesson id.
     */
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        lessonService.delete(id);
    }
}
