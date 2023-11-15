package com.core.service;

import com.core.EmployeeDTO;
import com.core.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getEmployeesList();

    Object saveEmployee(EmployeeDTO employeeDTO);
}
