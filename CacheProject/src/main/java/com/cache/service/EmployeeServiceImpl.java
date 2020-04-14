package com.cache.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cache.Employee;
import com.cache.EmployeeRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service(value = "userService")
public class EmployeeServiceImpl implements UserDetailsService, EmployeeService {

	@Autowired
	private EmployeeRepository repository;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		Employee emp = repository.getOne(Long.valueOf(id));

		if (emp == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(emp.getUserName(), emp.getPassword(),
				getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	public List<Employee> findAll() {
		List<Employee> list = new ArrayList<>();
		repository.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public Employee findById(Long id) {
		Optional<Employee> optionalUser = repository.findById(id);
		return optionalUser.isPresent() ? optionalUser.get() : null;
	}

	@Override
	public Employee save(Employee user) {
		return repository.save(user);
	}

}
