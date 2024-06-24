package net.javaspring.ems.service;

import net.javaspring.ems.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();

    UserDto getUserById(Long userId);

    UserDto createUser(UserDto userDto);

    UserDto deleteUserById(Long userId);

}
