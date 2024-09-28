package com.example.cdoback.controller;

import com.example.cdoback.service.MeetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/meet")
public class MeetController {

    private final MeetService jitsiMeetService;

    @GetMapping("/start")
    public ResponseEntity<Map<String, String>> startConference() {
        Map<String, String> response = new HashMap<>();
        response.put("roomName", jitsiMeetService.generateRoomName());
        response.put("message", "Присоединиться к конференции");

        return ResponseEntity.ok(response);
    }
}
