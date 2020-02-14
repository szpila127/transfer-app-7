package com.transfer.app7.mapper.user;

import com.transfer.app7.domain.User;
import com.transfer.app7.domain.dto.UserDto;
import com.transfer.app7.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTestSuite {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testMapToUser() {
        //Given
        UserDto userDto = new UserDto(10L, "sebek", "sebek", "919191919", new ArrayList<>());
        //When
        User user = userMapper.mapToUser(userDto);
        //Then
        assertEquals("sebek", user.getEmail());
        assertTrue(user.getAccounts().isEmpty());
    }

    @Test
    public void testMapToUserDto() {
        //Given
        User user = new User(7L, "krzys", "krzys", "727272727", new ArrayList<>());
        //When
        UserDto userDto = userMapper.mapToUserDto(user);
        //Then
        assertEquals("krzys", userDto.getEmail());
        assertTrue(userDto.getAccounts().isEmpty());
    }

    @Test
    public void testMapToUserDtoList() {
        //Given
        User user1 = new User(7L, "krzys", "krzys", "727272727", new ArrayList<>());
        User user2 = new User(10L, "sebek", "sebek", "919191919", new ArrayList<>());
        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);
        //When
        List<UserDto> listDto = userMapper.mapToUserDtoList(list);
        //Then
        assertEquals(2, listDto.size());
        assertEquals("sebek", listDto.get(1).getEmail());
    }
}
