package com.transfer.app7.facade;

import com.transfer.app7.controller.NotFoundException;
import com.transfer.app7.domain.UserDto;
import com.transfer.app7.mapper.UserMapper;
import com.transfer.app7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserFacade {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    public void createUser(UserDto userDto) {
        userService.save(userMapper.mapToUser(userDto));
    }

    public List<UserDto> getUsers() {
        return userMapper.mapToUserDtoList(userService.getAllUsers());
    }

    public Long countUsers() {
        return userService.countUsers();
    }

    public UserDto getUser(Long userId) throws NotFoundException {
        return userMapper.mapToUserDto(userService.getUser(userId).orElseThrow(NotFoundException::new));
    }

    public void deleteUser(Long userId) {
        userService.deleteUser(userId);
    }

    public UserDto updateUser(UserDto userDto) {
        return userMapper.mapToUserDto(userService.save(userMapper.mapToUser(userDto)));
    }
}
