package com.example.cdoback.service;

import com.example.cdoback.database.entity.Conference;
import com.example.cdoback.repository.ConferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConferenceService {

    private final ConferenceRepository conferenceRepository;

    public Conference createConference(Conference conference, String hostUsername) {
        conference.setHostUsername(hostUsername);
        conference.setConferenceUrl(generateUniqueUrl(conference.getConferenceName()));
        return conferenceRepository.save(conference);
    }

    public Optional<Conference> getConference(Long id) {
        return conferenceRepository.findById(id);
    }

    // Поиск конференции по id
    public Conference findConferenceById(Long id) {
        return conferenceRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Конференция не найдена"));
    }

    public List<Conference> findAllByHostUsername(String hostUsername) {
        return conferenceRepository.findAllByHostUsername(hostUsername);
    }

    public Optional<Conference> findConferenceByName(String conferenceName) {
        return conferenceRepository.findByConferenceName(conferenceName);
    }

    public void deleteConference(Long id) {
        conferenceRepository.deleteById(id);
    }

    public List<Conference> findAll() {
        return conferenceRepository.findAll();
    }

    private String generateUniqueUrl(String conferenceName) {
        // Generate a unique URL for the conference
        return "https://online3.spa.msu.ru/" + UUID.randomUUID() + "-" + conferenceName;
    }
}
