package com.transfer.app7.controller;

import com.transfer.app7.domain.dto.AppEventDto;
import com.transfer.app7.exception.NotFoundException;
import com.transfer.app7.facade.AppEventFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/ta7/event")
public class AppEventController {

    @Autowired
    private AppEventFacade appEventFacade;

    @PostMapping(consumes = "application/json")
    public void createEvent(@RequestBody AppEventDto appEventDto) {
        appEventFacade.createEvent(appEventDto);
    }

    @GetMapping
    public List<AppEventDto> getEvents() {
        return appEventFacade.getEvents();
    }

    @GetMapping(value = "/count")
    public Long countEvents() {
        return appEventFacade.countEvents();
    }

    @GetMapping(value = "/{id}")
    public AppEventDto getEvent(@PathVariable("id") Long eventId) throws NotFoundException {
        return appEventFacade.getEvent(eventId);
    }
}
