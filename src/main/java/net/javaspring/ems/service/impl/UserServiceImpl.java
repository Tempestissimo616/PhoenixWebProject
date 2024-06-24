package net.javaspring.ems.service.impl;

import lombok.AllArgsConstructor;
import net.javaspring.ems.dto.UserDto;
import net.javaspring.ems.entity.Department;
import net.javaspring.ems.entity.User;
import net.javaspring.ems.exception.ResourceNotFoundException;
import net.javaspring.ems.repository.DepartmentRepository;
import net.javaspring.ems.repository.UserRepository;
import net.javaspring.ems.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private DepartmentRepository departmentRepository;
    private ModelMapper modelMapper;

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return modelMapper.map(user, UserDto.class);
    }

    public UserDto createUser(UserDto userDto) {
        //这个方法会被 authService里的register 取代

        User user = modelMapper.map(userDto, User.class);
        Department department = departmentRepository.findById(userDto.getDepartmentId()).orElseThrow(() -> new ResourceNotFoundException("Department", "id", userDto.getDepartmentId()));
        user.setDepartment(department);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto deleteUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        userRepository.delete(user);
        return modelMapper.map(user, UserDto.class);
    }
}
