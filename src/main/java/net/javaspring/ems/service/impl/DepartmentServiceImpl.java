package net.javaspring.ems.service.impl;

import lombok.AllArgsConstructor;
import net.javaspring.ems.dto.DepartmentDto;
import net.javaspring.ems.entity.Department;
import net.javaspring.ems.exception.ResourceNotFoundException;
import net.javaspring.ems.mapper.DepartmentMapper;
import net.javaspring.ems.repository.DepartmentRepository;
import net.javaspring.ems.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    DepartmentRepository departmentRepository;

    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {

        Department department = DepartmentMapper.mapToDepartment(departmentDto);
        Department savedDepartment = departmentRepository.save(department);
        return DepartmentMapper.mapToDepartmentDto(savedDepartment);

    }

    @Override
    public DepartmentDto getDepartmentById(Long departmentId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new ResourceNotFoundException("Department", "id", departmentId));
        return DepartmentMapper.mapToDepartmentDto(department);
    }

    @Override
    public List<DepartmentDto> getAllDepartment() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map((department) -> DepartmentMapper.mapToDepartmentDto(department)).collect(Collectors.toList());
    }

    @Override
    public DepartmentDto updateDepartment(Long departmentId, DepartmentDto departmentDto) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new ResourceNotFoundException("Department", "id", departmentId));
        department.setDepartmentName(departmentDto.getDepartmentName());
        department.setDepartmentDescription(departmentDto.getDepartmentDescription());

        return DepartmentMapper.mapToDepartmentDto(departmentRepository.save(department));
    }

    @Override
    public void deleteDepartment(Long departmentId) {

        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new ResourceNotFoundException("Department", "id",departmentId));

        departmentRepository.deleteById(departmentId);

    }
}
