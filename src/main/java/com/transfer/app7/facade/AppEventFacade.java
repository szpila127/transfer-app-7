package com.transfer.app7.facade;

import com.transfer.app7.domain.dto.AppEventDto;
import com.transfer.app7.exception.NotFoundException;
import com.transfer.app7.mapper.AppEventMapper;
import com.transfer.app7.service.AppEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class AppEventFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppEventFacade.class);

    @Autowired
    private AppEventService appEventService;

    @Autowired
    private AppEventMapper appEventMapper;

    public void createEvent(AppEventDto appEventDto) {
        LOGGER.info("Creating an ApplicationEvent");
        appEventService.save(appEventMapper.mapToAppEvent(appEventDto));
    }

    public List<AppEventDto> getEvents() {
        LOGGER.info("Getting list of AppEvents");
        return appEventMapper.mapToAppEventDtoList(appEventService.getAllEvents());
    }

    public List<AppEventDto> getEventsByDate(LocalDate date) {
        LOGGER.info("Getting list of AppEvents bu date:" + date);
        return appEventMapper.mapToAppEventDtoList(appEventService.getByDate(date));
    }

    public Long countEvents() {
        LOGGER.info("Counting events");
        return appEventService.countEvents();
    }

    public AppEventDto getEvent(Long eventId) {
        LOGGER.info("Getting event by id: " + eventId);
        return appEventMapper.mapToAppEventDto(appEventService.getEvent(eventId).orElseThrow(NotFoundException::new));
    }
}
