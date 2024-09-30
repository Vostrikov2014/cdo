package com.example.cdoback.controller;

import com.example.cdoback.database.entity.Conference;
import com.example.cdoback.service.ConferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conference")
public class ConferenceController {

    private final ConferenceService conferenceService;

    @GetMapping("/list")
    public ResponseEntity<List<Conference>> listConferences(@AuthenticationPrincipal UserDetails userDetails) {

        String hostUsername = "xxx";
        if (userDetails != null) {
            hostUsername = userDetails.getUsername();
        }
        List<Conference> conferences = conferenceService.findAllByHostUsername(hostUsername);
        return ResponseEntity.ok(conferences);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conference> getConference(@PathVariable Long id) {
        Optional<Conference> conference = conferenceService.getConference(id);
        return conference.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<Conference> createConference(@RequestBody Conference conference, @AuthenticationPrincipal UserDetails userDetails) {
        //String hostUsername = userDetails.getUsername();
        String hostUsername = "xxx";
        Conference createdConference = conferenceService.createConference(conference, hostUsername);
        return ResponseEntity.ok(createdConference);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConference(@PathVariable Long id) {
        conferenceService.deleteConference(id);
        return ResponseEntity.noContent().build();
    }
}
