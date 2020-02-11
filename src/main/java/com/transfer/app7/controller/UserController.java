package com.transfer.app7.controller;

import com.transfer.app7.domain.dto.UserDto;
import com.transfer.app7.exception.NotFoundException;
import com.transfer.app7.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/ta7/user")
public class UserController {

    @Autowired
    private UserFacade userFacade;

    @PostMapping(consumes = "application/json")
    public String createUser(@RequestBody UserDto userDto) {
        return userFacade.createUser(userDto);
    }

    @GetMapping
    public List<UserDto> getUsers() {
        return userFacade.getUsers();
    }

    @GetMapping(value = "/count")
    public Long countUsers() {
        return userFacade.countUsers();
    }

    @GetMapping(value = "/{id}")
    public UserDto getUser(@PathVariable("id") Long userId) throws NotFoundException {
        return userFacade.getUser(userId);
    }

    @DeleteMapping(value = "/{id}")
    public String deleteUser(@PathVariable("id") Long userId) {
        return userFacade.deleteUser(userId);
    }

    @PutMapping(consumes = "application/json")
    public UserDto updateAllUser(@RequestBody UserDto userDto) {
        return userFacade.updateUser(userDto);
    }
}
