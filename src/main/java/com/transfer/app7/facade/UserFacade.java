package com.transfer.app7.facade;

import com.transfer.app7.client.EmailValidatorApiClient;
import com.transfer.app7.domain.Event;
import com.transfer.app7.domain.User;
import com.transfer.app7.domain.dto.AppEventDto;
import com.transfer.app7.domain.dto.UserDto;
import com.transfer.app7.exception.NotFoundException;
import com.transfer.app7.mapper.UserMapper;
import com.transfer.app7.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserFacade.class);

    @Autowired
    private EmailValidatorApiClient emailValidatorApiClient;

    @Autowired
    private AppEventFacade appEventFacade;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    public String createUser(UserDto userDto) {
        LOGGER.info("Creating an user...");
        boolean isValidEmail = emailValidatorApiClient.validateEmail(userDto.getEmail()).isValid();
        boolean exists = containsEmail(userService.getAllUsers(), userDto.getEmail());
        if (!exists) {
            if (isValidEmail) {
                userService.save(userMapper.mapToUser(userDto));
                AppEventDto appEventDto = new AppEventDto(
                        Event.CREATE,
                        "User - email: " + userDto.getEmail());
                appEventFacade.createEvent(appEventDto);
                LOGGER.info("User created.");
                return "User created.";
            } else {
                LOGGER.error("Invalid email!");
                return "Invalid email!";
            }
        } return "User exist!";
    }

    public boolean containsEmail(final List<User> userList, final String email ) {
        return userList.stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    public List<UserDto> getUsers() {
        LOGGER.info("Getting list of users");
        return userMapper.mapToUserDtoList(userService.getAllUsers());
    }

    public Long countUsers() {
        LOGGER.info("Counting users");
        return userService.countUsers();
    }

    public UserDto getUser(Long userId) {
        LOGGER.info("Getting user by id: " + userId);
        return userMapper.mapToUserDto(userService.getUser(userId).orElseThrow(NotFoundException::new));
    }

    public String deleteUser(Long userId) {
        AppEventDto appEventDto = new AppEventDto(
                Event.DELETE,
                "User id: " + userId + ", email: " + userService.getUser(userId).orElseThrow(NotFoundException::new).getEmail());
        appEventFacade.createEvent(appEventDto);
        userService.deleteUser(userId);
        LOGGER.info("User: " + userId + " deleted");
        return "User deleted.";
    }

    public UserDto updateUser(UserDto userDto) {
        AppEventDto appEventDto = new AppEventDto(
                Event.UPDATE,
                "User id: " + userDto.getId() +
                        ", email: " + userService.getUser(userDto.getId()).orElseThrow(NotFoundException::new).getEmail() + " -> " + userDto.getEmail() +
                        ", password: " + userService.getUser(userDto.getId()).orElseThrow(NotFoundException::new).getPassword() + " -> " + userDto.getPassword() +
                        ", pesel: " + userService.getUser(userDto.getId()).orElseThrow(NotFoundException::new).getPesel() + " -> " + userDto.getPesel());
        appEventFacade.createEvent(appEventDto);
        LOGGER.info("Updating user id: " + userDto.getId());
        return userMapper.mapToUserDto(userService.save(userMapper.mapToUser(userDto)));
    }
}
