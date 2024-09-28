package com.example.cdoback.controller;

import com.example.cdoback.database.entity.Conference;
import com.example.cdoback.service.ConferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {

    private final ConferenceService conferenceService;

    // Главная страница
    @GetMapping
    public String homePage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String hostUsername = userDetails.getUsername();
        // Добавляем список конференций, созданных пользователем
        model.addAttribute("conferences", conferenceService.findAllByHostUsername(hostUsername));
        return "home"; // Ссылка на home.html
    }

    // Страница создания конференции
    @GetMapping("/conference/create")
    public String showCreateConferenceForm(Model model) {
        model.addAttribute("conference", new Conference());
        return "createConference"; // Ссылка на createConference.html
    }

    // Обработка формы создания конференции
    @PostMapping("/conference/create")
    public String createConference(@ModelAttribute("conference") Conference conference,
                                   @AuthenticationPrincipal UserDetails userDetails) {
        conferenceService.createConference(conference, userDetails.getUsername());
        return "redirect:/"; // Перенаправление на главную страницу
    }

    // Запуск конференции
    @GetMapping("/conference/start/{id}")
    public String startConference(@PathVariable Long id, Model model) {
        Conference conference = conferenceService.findConferenceById(id);
        model.addAttribute("conferenceUrl", "https://online3.spa.msu.ru/" + conference.getConferenceName());
        return "conference"; // Ссылка на startConference.html для отображения конференции
    }

}
