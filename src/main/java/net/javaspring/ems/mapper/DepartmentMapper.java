package net.javaspring.ems.mapper;

import net.javaspring.ems.dto.DepartmentDto;
import net.javaspring.ems.entity.Department;

public class DepartmentMapper {

    public static DepartmentDto mapToDepartmentDto(Department department){
        return new DepartmentDto(department.getId(),department.getDepartmentName(),department.getDepartmentDescription());
    }

    public static Department mapToDepartment(DepartmentDto departmentDto){
        return new Department(departmentDto.getId(),departmentDto.getDepartmentName(),departmentDto.getDepartmentDescription());
    }
}
