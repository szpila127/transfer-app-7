package com.transfer.app7.controller;

import com.transfer.app7.domain.UserDto;
import com.transfer.app7.mapper.UserMapper;
import com.transfer.app7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/ta7/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping(consumes = "application/json")
    public void createUser(@RequestBody UserDto userDto) {
        userService.save(userMapper.mapToUser(userDto));
    }

    @GetMapping
    public List<UserDto> getUsers() {
        return userMapper.mapToUserDtoList(userService.getAllUsers());
    }

    @GetMapping(value = "/{id}")
    public UserDto getUser(@PathVariable("id") Long userId) throws NotFoundException{
        return userMapper.maToUserDto(userService.getUser(userId).orElseThrow(NotFoundException::new));
    }

    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
    }

    @PutMapping(consumes = "application/json")
    public UserDto updateAllUser(@RequestBody UserDto userDto) {
        return userMapper.maToUserDto(userService.save(userMapper.mapToUser(userDto)));
    }

    @PatchMapping(consumes = "application/json")
    public UserDto updateUserField(@RequestBody UserDto userDto) {
        return userMapper.maToUserDto(userService.save(userMapper.mapToUser(userDto)));
    }
}
