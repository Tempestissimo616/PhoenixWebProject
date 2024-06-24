package net.javaspring.ems.service;

import net.javaspring.ems.dto.DepartmentDto;
import net.javaspring.ems.entity.Department;

import java.util.List;

public interface DepartmentService {

    DepartmentDto createDepartment(DepartmentDto departmentDto);

    DepartmentDto getDepartmentById(Long departmentId);

    List<DepartmentDto> getAllDepartment();

    DepartmentDto updateDepartment(Long departmentId, DepartmentDto departmentDto);

    void deleteDepartment(Long departmentId);
}
