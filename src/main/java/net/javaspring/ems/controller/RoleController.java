package net.javaspring.ems.controller;

import lombok.AllArgsConstructor;
import net.javaspring.ems.dto.RoleDto;
import net.javaspring.ems.dto.UserRoleDto;
import net.javaspring.ems.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/role")
public class RoleController {

    RoleService roleService;

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto){
        RoleDto role = roleService.createRole(roleDto);
        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<RoleDto>> getAllRoles(){
        List<RoleDto> roleList = roleService.getAllRoles();
        return new ResponseEntity<>(roleList, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/setRole")
    public ResponseEntity<UserRoleDto> setUserRole(@RequestBody UserRoleDto userRoleDto){
        UserRoleDto userRole = roleService.setUserRole(userRoleDto);
        return new ResponseEntity<>(userRole, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/allUserRole")
    public ResponseEntity<List<UserRoleDto>> getAllUserAllRole(){
        return new ResponseEntity<>(roleService.getAllUserAllRole(), HttpStatus.OK);
    }

}
