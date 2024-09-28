package com.example.cdoback.controller;

import com.example.cdoback.database.entity.Conference;
import com.example.cdoback.service.ConferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conference")
public class ConferenceController {

    private final ConferenceService conferenceService;

    @GetMapping("/list")
    public String listConferences(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String hostUsername = userDetails.getUsername();
        // List conferences by the host or general
        model.addAttribute("conferences", conferenceService.findAllByHostUsername(hostUsername));
        return "conferenceList";
    }

    @GetMapping("/create")
    public String showCreateConferenceForm(Model model) {
        model.addAttribute("conference", new Conference());
        return "createСonference";
    }

    @PostMapping("/create")
    public String createConference(@ModelAttribute Conference conference, @AuthenticationPrincipal UserDetails userDetails) {
        String hostUsername = userDetails.getUsername();
        conferenceService.createConference(conference, hostUsername);
        return "redirect:/conference/list";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conference> getConference(@PathVariable Long id) {
        Optional<Conference> conference = conferenceService.getConference(id);
        return conference.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConference(@PathVariable Long id) {
        conferenceService.deleteConference(id);
        return ResponseEntity.noContent().build();
    }
}
