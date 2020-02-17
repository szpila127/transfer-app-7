package com.transfer.app7.mapper;

import com.transfer.app7.domain.AppEvent;
import com.transfer.app7.domain.Event;
import com.transfer.app7.domain.dto.AppEventDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AppEventMapperTestSuite {

    @Autowired
    private AppEventMapper appEventMapper;

    @Test
    public void testMapToAppEvent() {
        //Given
        AppEventDto appEventDto = new AppEventDto(1L, LocalDateTime.now(), Event.CREATE, "Test1");
        //When
        AppEvent appEvent = appEventMapper.mapToAppEvent(appEventDto);
        //Then
        assertEquals("Test1", appEvent.getInformation());
        assertEquals(Event.CREATE, appEvent.getEvent());
    }

    @Test
    public void testMapToAppEventDto() {
        //Given
        AppEvent appEvent = new AppEvent(1L, LocalDateTime.now(), Event.DELETE, "Test2");
        //When
        AppEventDto appEventDto = appEventMapper.mapToAppEventDto(appEvent);
        //Then
        assertEquals("Test2", appEventDto.getInformation());
        assertEquals(Event.DELETE, appEventDto.getEvent());
    }

    @Test
    public void testMapToAppEventDtoList() {
        //Given
        AppEvent appEvent1 = new AppEvent(1L, LocalDateTime.now(), Event.DELETE, "Test31");
        AppEvent appEvent2 = new AppEvent(2L, LocalDateTime.now(), Event.CREATE, "Test32");
        List<AppEvent> list = new ArrayList<>();
        list.add(appEvent1);
        list.add(appEvent2);
        //When
        List<AppEventDto> listDto = appEventMapper.mapToAppEventDtoList(list);
        //Then
        assertEquals("Test31", listDto.get(0).getInformation());
        assertEquals(Event.CREATE, listDto.get(1).getEvent());
        assertEquals(2, listDto.size());
    }
}
