package com.transfer.app7.service;

import com.transfer.app7.domain.AppEvent;
import com.transfer.app7.domain.Event;
import com.transfer.app7.repository.AppEventRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
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
        AppEvent appEvent1 = new AppEvent(1L, LocalDate.now(), LocalTime.now(), Event.CREATE, "Create");
        AppEvent appEvent2 = new AppEvent(2L, LocalDate.now(), LocalTime.now(), Event.DELETE, "Delete");
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
    public void testGetByDate() {
        //Given
        AppEvent appEvent1 = new AppEvent(1L, LocalDate.now(), LocalTime.now(), Event.CREATE, "Create");
        AppEvent appEvent2 = new AppEvent(2L, LocalDate.now(), LocalTime.now(), Event.DELETE, "Delete");
        List<AppEvent> list = new ArrayList<>();
        list.add(appEvent1);
        list.add(appEvent2);

        when(appEventRepository.findByDate(LocalDate.now())).thenReturn(list);
        //When
        List<AppEvent> listGet = appEventService.getByDate(LocalDate.now());
        //Then
        assertEquals(2, listGet.size());
        assertEquals(Event.CREATE, listGet.get(0).getEvent());
    }

    @Test
    public void testSave() {

    }
}
