package com.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cache.error.EmployeeNotSavedException;
import com.cache.service.EmployeeService;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService userService;
    @Autowired
    private EmployeeRepository employeeRepository;


    // Find
    @GetMapping("/cache-synch")
    List<Employee> findAll() {
        return userService.findAll();
    }

    // Save
    @PostMapping("/cache-synch")
    //return 201 instead of 200
   // @ResponseStatus(HttpStatus.CREATED)
    String save(@RequestBody Employee emp) {
        Employee emp2 = userService.save(emp);
       
        if(emp2 != null)
        	return "Success";
        else 
        return new EmployeeNotSavedException().getMessage();
    }
        
        @PostMapping("/signup")
        String signup(@RequestBody Employee emp) {
            Employee emp2 = employeeRepository.save(emp);
           
            if(emp2 != null)
            	return "Success";
            else {
            return new EmployeeNotSavedException().getMessage();
            }
    }

    
}
