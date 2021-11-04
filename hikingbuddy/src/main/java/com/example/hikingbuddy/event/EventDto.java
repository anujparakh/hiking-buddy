package com.example.hikingbuddy.event;

import java.util.Date;

import javax.persistence.Id;

import com.example.hikingbuddy.user.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EventDto {
    private Long id;
    private Long userID;
    private Date date;
    private String title;
    private String description;
    private String location;

    public EventDto(Event event) {
        this.id = event.getId();
        this.userID = event.getCreator().getId();
        this.date = event.getDate();
        this.title = event.getTitle();
        this.description = event.getDescription();
        this.location = event.getLocation();
    }

    public EventDto() {
    }

    public Event updateEventWithDto(Event existing) {
        return new Event(existing.getId(), existing.getCreator(), this.date, this.title, this.description,
                this.location);
    }

    public Event mergeWithUser(User creator) {
        return new Event(this.id, creator, this.date, this.title, this.description,
                this.location);

    }
}