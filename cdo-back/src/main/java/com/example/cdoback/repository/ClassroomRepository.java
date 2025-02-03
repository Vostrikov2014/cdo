package com.example.cdoback.repository;

import com.example.cdoback.entity.ClassroomEntity;
import com.example.cdoback.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface ClassroomRepository extends JpaRepository<ClassroomEntity, Long> {
}
