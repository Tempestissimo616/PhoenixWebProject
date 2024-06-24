package net.javaspring.ems.service;

import net.javaspring.ems.dto.RoleDto;
import net.javaspring.ems.dto.UserRoleDto;

import java.util.List;

public interface RoleService {

    RoleDto createRole(RoleDto roleDto);

    List<RoleDto> getAllRoles();

    UserRoleDto setUserRole(UserRoleDto userRoleDto);

    List<UserRoleDto> getAllUserAllRole();
}
