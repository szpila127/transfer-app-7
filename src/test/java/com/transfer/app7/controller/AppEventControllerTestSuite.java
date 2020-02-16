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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        AppEventDto appEventDto = new AppEventDto(1L, LocalDate.now(), LocalTime.now(), Event.CREATE, "Create");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(appEventDto);

        //When & Then
        mockMvc.perform(post("/v1/ta7/event")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }
}
