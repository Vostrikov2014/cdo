package com.example.cdoback.controller.schedule;

import com.example.cdoback.entity.LessonEntity;
import com.example.cdoback.exception.ResourceNotFoundException;
import com.example.cdoback.repository.LessonRepository;
import com.example.cdoback.service.schedule.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
//@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class LessonController {
    private final LessonRepository lessonRepository;
    private final LessonService lessonService;

    @GetMapping
    public List<LessonEntity> getAllLessons() {
        return lessonRepository.findAll();
    }

    @PostMapping
    public LessonEntity createLesson(@RequestBody LessonEntity lesson) {
        return lessonRepository.save(lesson);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLesson(@PathVariable Long id) {
        try {
            lessonService.deleteLesson(id);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting lesson");
        }
    }
}
