package com.example.hikingbuddy.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

import com.example.hikingbuddy.user.UserService;

@RestController
@RequestMapping("/api/events")
class EventController {
    @Autowired
    private EventService eventService;

    // GET ALL
    @GetMapping("")
    public ResponseEntity<List<EventDto>> getAllEvents()
    {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    // GET SINGLE
    @GetMapping("/{eventid}")
    public ResponseEntity<EventDto> getSingleEvent(@PathVariable(value="eventid") long eventID)
    {
        EventDto event = eventService.getEventById(eventID).orElse(null);
        if(event == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(event);
    }

    // UPDATE

    // CREATE
    @PostMapping("")
    public ResponseEntity<EventDto> createEvent(@RequestBody EventDto newEvent, Principal principal)
    {
        return ResponseEntity.ok(eventService.createEvent(newEvent, principal.getName()));
    }

    // DELETE
    @DeleteMapping("/{eventid}")
    public ResponseEntity<?> deleteEvent(@PathVariable(value="eventid") long eventID)
    {
        eventService.deleteEvent(eventID);
        return ResponseEntity.ok().build();
    }
}