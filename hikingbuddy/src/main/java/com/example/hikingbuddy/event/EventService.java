package com.example.hikingbuddy.event;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.hikingbuddy.user.User;
import com.example.hikingbuddy.user.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Autowired
    public EventService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    // GET ALL
    public List<EventDto> getAllEvents() {
        return eventRepository.findAll().stream().map(event -> new EventDto(event)).collect(Collectors.toList());

    }

    // GET SINGLE
    public Optional<EventDto> getEventById(Long id) {
        Event event = eventRepository.findById(id).orElse(null);
        if (event == null)
            return Optional.empty();
        else
            return Optional.ofNullable(new EventDto(event));
    }

    // UPDATE
    public EventDto updateEvent(Long id, EventDto updatedEvent)
    {
        Event event = eventRepository.getById(updatedEvent.getId());
        return new EventDto(eventRepository.save(updatedEvent.updateEventWithDto(event)));
    }

    // CREATE
    public EventDto createEvent(EventDto newEvent, String userEmail) {
        User creator = userRepository.findByEmail(userEmail).orElse(null);
        return new EventDto(eventRepository.save(newEvent.mergeWithUser(creator)));
    }

    // DELETE
    public void deleteEvent(long toDeleteID) {
        eventRepository.deleteById(toDeleteID);
    }
}