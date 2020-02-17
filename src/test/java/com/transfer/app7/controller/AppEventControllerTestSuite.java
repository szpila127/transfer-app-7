package com.transfer.app7.controller;

import com.google.gson.Gson;
import com.transfer.app7.domain.Event;
import com.transfer.app7.domain.dto.AppEventDto;
import com.transfer.app7.facade.AppEventFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AppEventController.class)
public class AppEventControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppEventFacade appEventFacade;

    @Test
    public void testCreateEvent() throws Exception {
        //Given
        AppEventDto appEventDto = new AppEventDto(10L, LocalDate.now(), LocalTime.now(), Event.CREATE, "Create");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(appEventDto);

        //When & Then
        mockMvc.perform(post("/v1/ta7/event")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetEvents() throws Exception {
        //Given
        AppEventDto appEventDto1 = new AppEventDto(10L, LocalDate.now(), LocalTime.now(), Event.CREATE, "Create");
        AppEventDto appEventDto2 = new AppEventDto(10L, LocalDate.now(), LocalTime.now(), Event.DELETE, "Delete");
        List<AppEventDto> listDto = new ArrayList<>();
        listDto.add(appEventDto1);
        listDto.add(appEventDto2);

        when(appEventFacade.getEvents()).thenReturn(listDto);

        //When & Then
        mockMvc.perform(get("/v1/ta7/event")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].information", is("Delete")));
    }

    @Test
    public void testGetEventsByDate() throws Exception {
        //Given
        AppEventDto appEventDto1 = new AppEventDto(10L, LocalDate.now(), LocalTime.now(), Event.CREATE, "Create");
        AppEventDto appEventDto2 = new AppEventDto(10L, LocalDate.now(), LocalTime.now(), Event.DELETE, "Delete");
        List<AppEventDto> listDto = new ArrayList<>();
        listDto.add(appEventDto1);
        listDto.add(appEventDto2);
        LocalDate localDate = LocalDate.parse("2020-10-10");

        when(appEventFacade.getEventsByDate(localDate)).thenReturn(listDto);

        //When & Then
        mockMvc.perform(get("/v1/ta7/event/date/2020-10-10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].information", is("Delete")));
    }

    @Test
    public void testCountEvents() throws Exception {
        //Given
        Long amount = 5L;

        when(appEventFacade.countEvents()).thenReturn(amount);

        //When & Then
        mockMvc.perform(get("/v1/ta7/event/count")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(5)));
    }

    @Test
    public void testGetEvent() throws Exception {
        //Given
        AppEventDto appEventDto1 = new AppEventDto(10L, LocalDate.now(), LocalTime.now(), Event.CREATE, "Create");

        when(appEventFacade.getEvent(10L)).thenReturn(appEventDto1);

        //When & Then
        mockMvc.perform(get("/v1/ta7/event/10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.information", is("Create")));
    }
}
