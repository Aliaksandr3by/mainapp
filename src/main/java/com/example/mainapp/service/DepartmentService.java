package com.example.mainapp.service;

import com.example.mainapp.exeptions.NotImplementedException;
import com.example.mainapp.model.DepartmentContext;
import com.example.mainapp.model.entity.Department;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@Service
@RequestScope
public class DepartmentService implements IService<Department> {

	private DepartmentContext departmentContext;

	public DepartmentService() {
	}

	@Autowired
	public DepartmentService(@Qualifier("departmentContext") DepartmentContext departmentContext) {
		this.departmentContext = departmentContext;
	}

	@Override
	public List<Department> getAll(String sortOrder) throws Exception {

		return this.departmentContext.getAll(sortOrder);
	}

	@Override
	public Department load(Department item) throws ObjectNotFoundException {

		return this.departmentContext.load(item);
	}

	@Override
	public Department create(Department item) {

		return this.departmentContext.create(item);
	}

	@Override
	public Department delete(Department item) {

		return this.departmentContext.delete(item);
	}

	@Override
	public Department update(Department item) {
		throw new NotImplementedException("Method is not implemented");
	}

	@Override
	public Department patch(Department item) {
		throw new NotImplementedException("Method is not implemented");
	}
}
