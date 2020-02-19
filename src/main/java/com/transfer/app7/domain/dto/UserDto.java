package com.transfer.app7.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private long id;
    private String email;
    private String password;
    private String pesel;
    private List<AccountDto> accounts = new ArrayList<>();
}
