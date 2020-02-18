package com.transfer.app7.facade;

import com.transfer.app7.client.EmailValidatorApiClient;
import com.transfer.app7.domain.User;
import com.transfer.app7.domain.dto.UserDto;
import com.transfer.app7.mapper.UserMapper;
import com.transfer.app7.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserFacadeTestSuite {

    @InjectMocks
    private UserFacade userFacade;

    @Mock
    private EmailValidatorApiClient emailValidatorApiClient;

    @Mock
    private AppEventFacade appEventFacade;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Test
    public void testGetUsers() {
        //Given
        User user = new User(1L, "sebek", "sebek", "919191919", new ArrayList<>());
        List<User> list = new ArrayList<>();
        list.add(user);
        UserDto userDto = new UserDto(1L, "sebek", "sebek", "919191919", new ArrayList<>());
        List<UserDto> listDto = new ArrayList<>();
        listDto.add(userDto);

        when(userMapper.mapToUserDtoList(any())).thenReturn(listDto);
        when(userService.getAllUsers()).thenReturn(list);
        //When
        List<UserDto> listGet = userFacade.getUsers();
        //Then
        assertEquals(1, listGet.size());
    }
}
