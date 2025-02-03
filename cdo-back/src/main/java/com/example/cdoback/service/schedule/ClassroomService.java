package com.example.cdoback.service.schedule;

import com.example.cdoback.entity.ClassroomEntity;
import com.example.cdoback.repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomService {
    @Autowired
    private ClassroomRepository classroomRepository;

    public ClassroomEntity createClassroom(ClassroomEntity classroom) {
        return classroomRepository.save(classroom);
    }

    public List<ClassroomEntity> getAllClassrooms() {
        return classroomRepository.findAll();
    }

    public void deleteClassroom(Long id) {
        classroomRepository.deleteById(id);
    }
}
