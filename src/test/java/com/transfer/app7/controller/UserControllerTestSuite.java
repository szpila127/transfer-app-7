package com.transfer.app7.controller;

import com.google.gson.Gson;
import com.transfer.app7.domain.dto.UserDto;
import com.transfer.app7.facade.UserFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserFacade userFacade;

    @Test
    public void testCreateUser() throws Exception {
        //Given
        UserDto userDto = new UserDto(2L, "sebek", "sebek", "929292929", new ArrayList<>());

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);

        //When & Then
        mockMvc.perform(post("/v1/ta7/user")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetEmptyUsersList() throws Exception {
        //Given
        List<UserDto> listDto = new ArrayList<>();

        when(userFacade.getUsers()).thenReturn(listDto);

        //When & Then
        mockMvc.perform(get("/v1/ta7/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testGetUsersList() throws Exception {
        //Given
        UserDto userDto1 = new UserDto(1L, "sebek", "sebek", "929292929", new ArrayList<>());
        UserDto userDto2 = new UserDto(2L, "krzys", "krzys", "929292929", new ArrayList<>());
        List<UserDto> listDto = new ArrayList<>();
        listDto.add(userDto1);
        listDto.add(userDto2);

        when(userFacade.getUsers()).thenReturn(listDto);

        //When & Then
        mockMvc.perform(get("/v1/ta7/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].email", is("sebek")));
    }

    @Test
    public void testCountUsers() throws Exception {
        //Given
        when(userFacade.countUsers()).thenReturn(10L);

        //When & Then
        mockMvc.perform(get("/v1/ta7/user/count")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(10)));
    }

    @Test
    public void testGetUser() throws Exception {
        //Given
        UserDto userDto1 = new UserDto(1L, "sebek", "sebek", "929292929", new ArrayList<>());

        when(userFacade.getUser(1L)).thenReturn(userDto1);

        //When & Then
        mockMvc.perform(get("/v1/ta7/user/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("sebek")));
    }

    @Test
    public void testDeleteUser() throws Exception {
        //Given
        when(userFacade.deleteUser(1L)).thenReturn("Deleted");

        //When & Then
        mockMvc.perform(delete("/v1/ta7/user/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Deleted")));
        verify(userFacade, times(1)).deleteUser(1L);
    }

    @Test
    public void testUpdateUser() throws Exception {
        //Given
        UserDto userDto1 = new UserDto(1L, "sebek", "sebek", "929292929", new ArrayList<>());

        when(userFacade.updateUser(ArgumentMatchers.any(UserDto.class))).thenReturn(userDto1);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto1);

        //When & Then
        mockMvc.perform(put("/v1/ta7/user")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
        verify(userFacade, times(1)).updateUser(ArgumentMatchers.any(UserDto.class));
    }
}
