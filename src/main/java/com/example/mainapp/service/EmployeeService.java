package com.example.mainapp.service;

import com.example.mainapp.model.DataContext;
import com.example.mainapp.model.EmployeeContext;
import com.example.mainapp.model.entity.Employee;
import lombok.Getter;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@Service("employeeService")
@RequestScope
public class EmployeeService implements DataContext<Employee> {

	@Getter
	private EmployeeContext context;

	public EmployeeService() {
	}

	@Autowired
	public EmployeeService(@Qualifier("employeeContext") EmployeeContext departmentContext) {
		this.context = departmentContext;
	}

	@Override
	public List<Employee> getAll(String sortOrder)  {

		return this.context.getAll(sortOrder);
	}

	@Override
	public Employee load(Employee item) throws ObjectNotFoundException {

		return this.context.load(item);
	}

	@Override
	public Employee create(Employee item){

		return this.context.create(item);
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
