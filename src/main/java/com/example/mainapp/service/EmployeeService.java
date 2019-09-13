package com.example.mainapp.service;

import com.example.mainapp.model.EmployeeContext;
import com.example.mainapp.model.entity.Employee;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@Service
@RequestScope
public class EmployeeService implements IService<Employee> {

	private EmployeeContext context;

	public EmployeeService() {
	}

	@Autowired
	public EmployeeService(@Qualifier("employeeContext") EmployeeContext context) {
		this.context = context;
	}

	@Override
	public List<Employee> findAll(String sortOrder) {

		return this.context.findAll(sortOrder);
	}

	@Override
	public Employee findById(Employee item) throws ObjectNotFoundException {

		return this.context.findById(item);
	}

	@Override
	public Employee insert(Employee item) {

		return this.context.insert(item);
	}

	@Override
	public Employee delete(Employee item) {

		return this.context.delete(item);
	}

	@Override
	public Employee update(Employee item) {

		return this.context.update(item);
	}

	@Override
	public Employee patch(Employee item) {

		return this.context.patch(item);
	}
}
