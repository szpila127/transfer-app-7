package com.transfer.app7.facade;

import com.transfer.app7.domain.dto.AppEventDto;
import com.transfer.app7.exception.NotFoundException;
import com.transfer.app7.mapper.AppEventMapper;
import com.transfer.app7.service.AppEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public Long countEvents() {
        return appEventService.countEvents();
    }

    public AppEventDto getEvent(Long eventId) throws NotFoundException {
        return appEventMapper.mapToAppEventDto(appEventService.getEvent(eventId).orElseThrow(NotFoundException::new));
    }

    public void deleteEvent(Long eventId) {
        appEventService.deleteEvent(eventId);
    }


    public AppEventDto updateEvent(AppEventDto appEventDto) {
        return appEventMapper.mapToAppEventDto(appEventService.save(appEventMapper.mapToAppEvent(appEventDto)));
    }
}
