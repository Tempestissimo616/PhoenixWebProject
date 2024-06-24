package net.javaspring.ems.service.impl;

import lombok.AllArgsConstructor;
import net.javaspring.ems.dto.RoleDto;
import net.javaspring.ems.dto.UserDto;
import net.javaspring.ems.dto.UserRoleDto;
import net.javaspring.ems.entity.Role;
import net.javaspring.ems.entity.User;
import net.javaspring.ems.exception.ResourceNotFoundException;
import net.javaspring.ems.repository.RoleRepository;
import net.javaspring.ems.repository.UserRepository;
import net.javaspring.ems.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class  RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;
    UserRepository userRepository;
    ModelMapper modelMapper;

    @Override
    public RoleDto createRole(RoleDto roleDto) {

        Role role = modelMapper.map(roleDto, Role.class);
        Role savedRole = roleRepository.save(role);

        return modelMapper.map(savedRole,RoleDto.class);
    }

    @Override
    public List<RoleDto> getAllRoles() {
        List<Role> roleList = roleRepository.findAll();
        return roleList.stream().map(role -> modelMapper.map(role,RoleDto.class)).collect(Collectors.toList());
    }

    @Override
    public UserRoleDto setUserRole(UserRoleDto userRoleDto) {
        User user = userRepository.findById(userRoleDto.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", userRoleDto.getUserId()));
        Set<Role> roleList = userRoleDto.getRoles().stream().map(role -> roleRepository.findById(role.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", role.getId()))).collect(Collectors.toSet());

        user.getRoles().addAll(roleList);
        User savedUser = userRepository.save(user);
        UserRoleDto result = new UserRoleDto();
        result.setUserId(savedUser.getId());
        result.setRoles(savedUser.getRoles().stream().map(r -> modelMapper.map(r,RoleDto.class)).collect(Collectors.toSet()));

        return result;
    }

    public List<UserRoleDto> getAllUserAllRole(){
        List<User> userList = userRepository.findAll();


        List<UserRoleDto> userRoleDtoList = new ArrayList<>();
        for (User user : userList) {
            UserRoleDto userRoleDto = new UserRoleDto();
            userRoleDto.setUserId(user.getId());

            Set<RoleDto> roleDtoSet = user.getRoles().stream()
                    .map(role -> new RoleDto(role.getId(), role.getName()))
                    .collect(Collectors.toSet());

           userRoleDto.setRoles(roleDtoSet);
           userRoleDtoList.add(userRoleDto);
        }

        return userRoleDtoList;
    }


}
