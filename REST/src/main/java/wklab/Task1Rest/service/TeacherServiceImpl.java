package wklab.Task1Rest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import wklab.Task1Rest.domain.Teacher;
import wklab.Task1Rest.dto.TeacherDto;
import wklab.Task1Rest.repository.TeacherRepository;
import wklab.Task1Rest.service.iface.ITeacherService;

@Service
public class TeacherServiceImpl implements ITeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public Page<Teacher> getAll(Pageable pageable) {
        return teacherRepository.findAll(pageable);
    }

    @Override
    public Teacher getTeacherById(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Teacher not found");
        }
        return teacherRepository.getOne(id);
    }

    @Override
    public Teacher create(TeacherDto teacherDto) {
        Teacher teacher = new Teacher();
        teacher.setName(teacherDto.getName());
        teacher.setBirthday(teacherDto.getBirthday());
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher update(Long id, TeacherDto teacherDto) {
        Teacher teacher = getTeacherById(id);
        boolean isChanged = false;
        if (!teacher.getName().equals(teacherDto.getName())) {
            teacher.setName(teacherDto.getName());
            isChanged = true;
        }
        if (!teacher.getBirthday().equals(teacherDto.getBirthday())) {
            teacher.setBirthday(teacherDto.getBirthday());
            isChanged = true;
        }
        if(!isChanged){
            throw new ResponseStatusException(
                    HttpStatus.NOT_MODIFIED, "Teacher not modified");
        }
        return teacherRepository.save(teacher);
    }

    @Override
    public void delete(Long id) {
        teacherRepository.delete(getTeacherById(id));
    }

    @Override
    public Teacher getBusiestTeacher() {
        return teacherRepository.getBusiestTeacher();
    }
}
