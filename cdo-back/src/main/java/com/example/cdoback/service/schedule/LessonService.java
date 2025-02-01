package com.example.cdoback.service.schedule;

import com.example.cdoback.entity.LessonEntity;
import com.example.cdoback.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;

    public void deleteLesson(Long id) {
        // Проверяем, существует ли урок с таким id
        LessonEntity lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + id));

        // Удаляем урок
        lessonRepository.delete(lesson);
    }
}
