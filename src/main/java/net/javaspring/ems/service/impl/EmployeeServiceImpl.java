package net.javaspring.ems.service.impl;

import lombok.AllArgsConstructor;
import net.javaspring.ems.dto.EmployeeDto;
import net.javaspring.ems.entity.Department;
import net.javaspring.ems.entity.Employee;
import net.javaspring.ems.exception.ResourceNotFoundException;
import net.javaspring.ems.mapper.EmployeeMapper;
import net.javaspring.ems.repository.DepartmentRepository;
import net.javaspring.ems.repository.EmployeeRepository;
import net.javaspring.ems.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {


    private EmployeeRepository employeeRepository;
    private DepartmentRepository departmentRepository;
    private ModelMapper modelMapper;


    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

//        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
//        Department department = departmentRepository.findById(employeeDto.getDepartmentId()).orElseThrow(() -> new ResourceNotFoundException("DepartmentId is not exists with id:" + employeeDto.getDepartmentId()));
//        employee.setDepartment(department);
//
//        Employee savedEmployee = employeeRepository.save(employee);
//
//        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
        //

        Employee employee = modelMapper.map(employeeDto,Employee.class);

        Employee savedEmployee = employeeRepository.save(employee);

        return modelMapper.map(savedEmployee,EmployeeDto.class);

    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));

        return modelMapper.map(employee,EmployeeDto.class);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {

        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map((employee -> modelMapper.map(employee,EmployeeDto.class))).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));
        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());

        Department department = departmentRepository.findById(updatedEmployee.getDepartmentId()).orElseThrow(() -> new ResourceNotFoundException("Department", "id", updatedEmployee.getDepartmentId()));
        employee.setDepartment(department);

        Employee updatedEmployeeObj = employeeRepository.save(employee);

        return modelMapper.map(updatedEmployeeObj,EmployeeDto.class);
    }

    @Override
    public void deleteEmployee(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));

        employeeRepository.deleteById(employeeId);
    }
}
