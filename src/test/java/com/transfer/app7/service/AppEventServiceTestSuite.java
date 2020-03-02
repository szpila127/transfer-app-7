package com.transfer.app7.service;

import com.transfer.app7.domain.AppEvent;
import com.transfer.app7.domain.Event;
import com.transfer.app7.repository.AppEventRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppEventServiceTestSuite {

    @InjectMocks
    private AppEventService appEventService;

    @Mock
    private AppEventRepository appEventRepository;

    @Test
    public void testGetAllEvents() {
        //Given
        AppEvent appEvent1 = new AppEvent(1L, LocalDateTime.now(), Event.CREATE, "Create");
        AppEvent appEvent2 = new AppEvent(2L, LocalDateTime.now(), Event.DELETE, "Delete");
        List<AppEvent> list = new ArrayList<>();
        list.add(appEvent1);
        list.add(appEvent2);

        when(appEventRepository.findAll()).thenReturn(list);
        //When
        List<AppEvent> listGet = appEventService.getAllEvents();
        //Then
        assertEquals(2, listGet.size());
        assertEquals(Event.DELETE, listGet.get(1).getEvent());
    }

    @Test
    public void testSave() {
        //Given
        AppEvent appEvent1 = new AppEvent(1L, LocalDateTime.now(), Event.CREATE, "Create");

        when(appEventRepository.save(any(AppEvent.class))).thenReturn(appEvent1);
        //When
        AppEvent appEventSave = appEventService.save(appEvent1);
        //Then
        assertEquals(Event.CREATE, appEventSave.getEvent());
    }

    @Test
    public void testGetEvent() {
        //Given
        AppEvent appEvent1 = new AppEvent(11L, LocalDateTime.now(), Event.CREATE, "Create");

        when(appEventRepository.findById(11L)).thenReturn(java.util.Optional.of(appEvent1));
        //When
        Optional<AppEvent> appEventGet = appEventService.getEvent(11L);
        //Then
        assertEquals("Create", appEventGet.get().getInformation());
    }

    @Test
    public void testCountEvent() {
        //Given
        when(appEventRepository.count()).thenReturn(15L);
        //When
        long amount = appEventService.countEvents();
        //Then
        assertEquals(15L, amount);
    }
}
