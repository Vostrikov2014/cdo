package com.example.cdoback.controller;

import com.example.cdoback.service.JitsiMeetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/meet")
public class MeetController {

    private final JitsiMeetService jitsiMeetService;

    @GetMapping("/start")
    public String startConference(Model model) {
        model.addAttribute("roomName", jitsiMeetService.generateRoomName());
        model.addAttribute("message", "Присоединиться к конференции");
        return "conference";  // Ссылается на HTML-шаблон Thymeleaf
    }
}
