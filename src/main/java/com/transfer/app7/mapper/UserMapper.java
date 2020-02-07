package com.transfer.app7.mapper;

import com.transfer.app7.domain.User;
import com.transfer.app7.domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    @Autowired
    private AccountMapper accountMapper;

    public User mapToUser(final UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getLogin(),
                userDto.getPassword(),
                userDto.getPesel(),
                accountMapper.mapToAccountList(userDto.getAccounts()));
    }

    public User mapToUserAdd(final UserDto userDto) {
        return new User(
                userDto.getLogin(),
                userDto.getPassword(),
                userDto.getPesel());
    }


    public UserDto maToUserDto(final User user) {
        return new UserDto(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                user.getPesel(),
                accountMapper.mapToAccountDtoList(user.getAccounts()));
    }

    public List<UserDto> mapToUserDtoList(final List<User> userList) {
        return userList.stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getLogin(),
                        user.getPassword(),
                        user.getPesel(),
                        accountMapper.mapToAccountDtoList(user.getAccounts())))
                .collect(Collectors.toList());
    }

    public List<User> mapToUserList(final List<UserDto> userDtoList) {
        return userDtoList.stream()
                .map(userDto -> new User(
                        userDto.getId(),
                        userDto.getLogin(),
                        userDto.getPassword(),
                        userDto.getPesel(),
                        accountMapper.mapToAccountList(userDto.getAccounts())))
                .collect(Collectors.toList());
    }
}
