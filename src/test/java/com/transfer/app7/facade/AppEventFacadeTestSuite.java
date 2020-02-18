package com.transfer.app7.facade;

import com.transfer.app7.domain.AppEvent;
import com.transfer.app7.domain.Event;
import com.transfer.app7.domain.dto.AppEventDto;
import com.transfer.app7.mapper.AppEventMapper;
import com.transfer.app7.service.AppEventService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AppEventFacadeTestSuite {

    @InjectMocks
    private AppEventFacade appEventFacade;

    @Mock
    private AppEventService appEventService;

    @Mock
    private AppEventMapper appEventMapper;

    @Test
    public void testCreateEvent() {
        //Given
        AppEventDto appEventDto = new AppEventDto(1L, LocalDateTime.now(), Event.CREATE, "Create");
        AppEvent appEvent = new AppEvent(1L, LocalDateTime.now(), Event.CREATE, "Create");

        when(appEventMapper.mapToAppEvent(appEventDto)).thenReturn(appEvent);
        when(appEventService.save(appEvent)).thenReturn(appEvent);
        //When
        appEventFacade.createEvent(appEventDto);
        //Then
        verify(appEventService, times(1)).save(appEvent);
    }

    @Test
    public void testGetEvents() {
        //Given
        AppEventDto appEventDto1 = new AppEventDto(1L, LocalDateTime.now(), Event.CREATE, "Create");
        AppEventDto appEventDto2 = new AppEventDto(2L, LocalDateTime.now(), Event.DELETE, "Delete");
        List<AppEventDto> listDto = new ArrayList<>();
        listDto.add(appEventDto1);
        listDto.add(appEventDto2);
        AppEvent appEvent1 = new AppEvent(1L, LocalDateTime.now(), Event.CREATE, "Create");
        AppEvent appEvent2 = new AppEvent(1L, LocalDateTime.now(), Event.CREATE, "Create");
        List<AppEvent> list = new ArrayList<>();
        list.add(appEvent1);
        list.add(appEvent2);

        when(appEventService.getAllEvents()).thenReturn(list);
        when(appEventMapper.mapToAppEventDtoList(list)).thenReturn(listDto);
        //When
        List<AppEventDto> listGet = appEventFacade.getEvents();
        //Then
        assertEquals(2, listGet.size());
        verify(appEventService, times(1)).getAllEvents();
    }

    @Test
    public void testCountEvents() {
        //Given
        when(appEventService.countEvents()).thenReturn(11L);
        //When
        long amount = appEventFacade.countEvents();
        //Then
        assertEquals(11L, amount);
    }

    @Test
    public void testGetEvent() {
        //Given
        AppEventDto appEventDto = new AppEventDto(1L, LocalDateTime.now(), Event.CREATE, "Create");
        AppEvent appEvent = new AppEvent(1L, LocalDateTime.now(), Event.CREATE, "Create");

        when(appEventMapper.mapToAppEventDto(appEvent)).thenReturn(appEventDto);
        when(appEventService.getEvent(1L)).thenReturn(java.util.Optional.of(appEvent));
        //When
        AppEventDto appEventDtoGet = appEventFacade.getEvent(1L);
        //Then
        assertEquals(Event.CREATE, appEventDtoGet.getEvent());
    }
}
