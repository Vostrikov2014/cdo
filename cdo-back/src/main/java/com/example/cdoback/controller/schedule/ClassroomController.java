package com.example.cdoback.controller.schedule;

import com.example.cdoback.entity.ClassroomEntity;
import com.example.cdoback.service.schedule.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classrooms")
public class ClassroomController {
    @Autowired
    private ClassroomService classroomService;

    @PostMapping
    public ResponseEntity<ClassroomEntity> createClassroom(@RequestBody ClassroomEntity classroom) {
        ClassroomEntity createdClassroom = classroomService.createClassroom(classroom);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClassroom);
    }

    @GetMapping
    public ResponseEntity<List<ClassroomEntity>> getAllClassrooms() {
        List<ClassroomEntity> classrooms = classroomService.getAllClassrooms();
        return ResponseEntity.ok(classrooms);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClassroom(@PathVariable Long id) {
        classroomService.deleteClassroom(id);
        return ResponseEntity.noContent().build();
    }
}
