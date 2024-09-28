package com.example.cdoback.repository;

import com.example.cdoback.database.entity.Conference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConferenceRepository extends JpaRepository<Conference, Long> {
    Optional<Conference> findByConferenceName(String title);
    List<Conference> findAllByHostUsername(String hostUsername);
}
