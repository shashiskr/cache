package com.cache.service;

import java.util.List;

import com.cache.Employee;

public interface EmployeeService {

    Employee save(Employee user);
    List<Employee> findAll();

    Employee findById(Long id);

   
}
