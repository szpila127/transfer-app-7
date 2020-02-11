package com.transfer.app7.facade;

import com.transfer.app7.domain.Event;
import com.transfer.app7.domain.dto.AppEventDto;
import com.transfer.app7.exception.NotFoundException;
import com.transfer.app7.mapper.AppEventMapper;
import com.transfer.app7.service.AppEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class AppEventFacade {

    @Autowired
    private AppEventService appEventService;

    @Autowired
    private AppEventMapper appEventMapper;

    public void createEvent(AppEventDto appEventDto) {
        appEventService.save(appEventMapper.mapToAppEvent(appEventDto));
    }

    public List<AppEventDto> getEvents() {
        return appEventMapper.mapToAppEventDtoList(appEventService.getAllEvents());
    }

    public List<AppEventDto> getEventsByDate(LocalDate date) {
        return appEventMapper.mapToAppEventDtoList(appEventService.getByDate(date));
    }

    public List<AppEventDto> getEventsByType(Event event) {
        return appEventMapper.mapToAppEventDtoList(appEventService.getByType(event));
    }

    public Long countEvents() {
        return appEventService.countEvents();
    }

    public AppEventDto getEvent(Long eventId) throws NotFoundException {
        return appEventMapper.mapToAppEventDto(appEventService.getEvent(eventId).orElseThrow(NotFoundException::new));
    }
}
