package com.example.cdoback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "conferences")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ConferenceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String conferenceName;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Column(nullable = false, unique = true)
    private String conferenceUrl;

    @Column(nullable = false)
    private String hostUsername;
}
