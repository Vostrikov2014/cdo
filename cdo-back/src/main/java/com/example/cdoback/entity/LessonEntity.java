package com.example.cdoback.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity(name = "lessons")
@Getter
@Setter
public class LessonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private StudentEntity student;

    @ManyToOne
    private ClassroomEntity classroom;

    private LocalTime startTime;
    private int durationMinutes;
    private DayOfWeek dayOfWeek;
}
