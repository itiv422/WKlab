package wklab.Task1Rest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import wklab.Task1Rest.domain.Lesson;
import wklab.Task1Rest.dto.LessonDto;
import wklab.Task1Rest.repository.LessonRepository;
import wklab.Task1Rest.service.iface.ILessonService;
import wklab.Task1Rest.service.iface.ITeacherService;

@Service
public class LessonServiceImpl implements ILessonService {
    final private LessonRepository lessonRepository;
    final private ITeacherService teacherService;

    public LessonServiceImpl(LessonRepository lessonRepository, ITeacherService teacherService) {
        this.lessonRepository = lessonRepository;
        this.teacherService = teacherService;
    }

    @Override
    public Page<Lesson> getAll(Pageable pageable) {
        return lessonRepository.findAll(pageable);
    }

    @Override
    public Lesson getLessonById(Long id) {
        if (!lessonRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Lesson not found");
        }
        return lessonRepository.getOne(id);
    }

    @Override
    public Lesson create(LessonDto lessonDto) {
        Lesson lesson = new Lesson();
        lesson.setDuration(lessonDto.getDuration());
        lesson.setName(lessonDto.getName());
        lesson.setTeacher(teacherService.getTeacherById(lessonDto.getTeacherId()));
        return lessonRepository.save(lesson);
    }

    @Override
    public Lesson update(Long id, LessonDto lessonDto) {
        Lesson lesson = getLessonById(id);
        boolean isChanged = false;
        if(!lesson.getDuration().equals(lessonDto.getDuration())) {
            lesson.setDuration(lessonDto.getDuration());
            isChanged = true;
        }
        if(!lesson.getName().equals(lessonDto.getName())) {
            lesson.setName(lessonDto.getName());
            isChanged = true;
        }
        if(!isChanged){
            throw new ResponseStatusException(
                    HttpStatus.NOT_MODIFIED, "Lesson not modified");
        }
        return lessonRepository.save(lesson);
    }

    @Override
    public void delete(Long id) {
        lessonRepository.delete(getLessonById(id));
    }
}
