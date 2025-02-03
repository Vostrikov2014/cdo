package com.example.cdoback.repository;

import com.example.cdoback.entity.ClassroomEntity;
import com.example.cdoback.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
}
