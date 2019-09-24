package com.example.mainapp.service;

import com.example.mainapp.exeptions.NotImplementedException;
import com.example.mainapp.model.DepartmentContext;
import com.example.mainapp.entity.Department;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
	public DepartmentService(DepartmentContext departmentContext) {
		this.departmentContext = departmentContext;
	}

	@Override
	public List<Department> findAll(String sortOrder) throws Exception {

		return this.departmentContext.findAll(sortOrder);
	}

	@Override
	public Department findById(Department item) throws ObjectNotFoundException {

		return this.departmentContext.findById(item);
	}

	@Override
	public Department insert(Department item) {

		return this.departmentContext.insert(item);
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
