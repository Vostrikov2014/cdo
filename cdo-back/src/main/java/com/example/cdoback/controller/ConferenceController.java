package com.example.cdoback.controller;

import com.example.cdoback.entity.ConferenceEntity;
import com.example.cdoback.service.ConferenceService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ConferenceController {

    private final ConferenceService conferenceService;
    private final AuditorAware<String> auditorAware; // пример - это можно удалить
    private final HttpSession session;

    @GetMapping("/conferences")
    public ResponseEntity<List<ConferenceEntity>> getConferences(@AuthenticationPrincipal UserDetails userDetails) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));

        if (username.equals("anonymousUser")) {
            return ResponseEntity.ok(conferenceService.findAll());
        } else if (userDetails != null) {
            return ResponseEntity.ok(conferenceService.findAllByHostUsername(userDetails.getUsername()));
        } else {
            return ResponseEntity.ok(conferenceService.findAllByHostUsername("xxx"));
        }
    }

    @GetMapping("/conference/{id}")
    public ResponseEntity<ConferenceEntity> getConference(@PathVariable Long id) {
        Optional<ConferenceEntity> conference = conferenceService.getConference(id);
        return conference.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/conferences")
    public ResponseEntity<ConferenceEntity> createConference(@RequestBody ConferenceEntity conference,
                                                             @CurrentSecurityContext(expression = "authentication") Authentication authentication,
                                                             @AuthenticationPrincipal UserDetails userDetails) {
        String hostUsername = "null";
        if (authentication != null && authentication.isAuthenticated()) {
            hostUsername =  authentication.getName();
        }

        ConferenceEntity createdConference = conferenceService.createConference(conference, hostUsername);
        return ResponseEntity.ok(createdConference);
    }

    @PutMapping("/conferences")
    public ResponseEntity<ConferenceEntity> updateConference(@RequestBody ConferenceEntity conference,
                                                       @AuthenticationPrincipal UserDetails userDetails) {
        ConferenceEntity savedConference = conferenceService.updateConference(conference);
        return ResponseEntity.ok(savedConference);
    }

    @DeleteMapping("conference/{id}")
    public ResponseEntity<Void> deleteConference(@PathVariable Long id) {
        conferenceService.deleteConference(id);
        return ResponseEntity.noContent().build();
    }
}
