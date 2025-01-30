package com.example.cdoback.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Conference {
    private Long id;
    private String conferenceName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String conferenceUrl;
    private String hostUsername;
}
