package com.example.mainapp.service;

import com.example.mainapp.model.DepartmentContext;
import com.example.mainapp.model.entity.Department;
import lombok.Getter;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@Service("departmentService")
@RequestScope
public class DepartmentService {

	@Getter
	private DepartmentContext departmentContext;

	public DepartmentService() {
	}

	@Autowired
	public DepartmentService(@Qualifier("departmentContext") DepartmentContext departmentContext) {
		this.departmentContext = departmentContext;
	}

	public List<Department> getDepartments(String sortOrder) throws Exception {

		return this.departmentContext.getAll(sortOrder);
	}

	public Department getDepartment(Department item) throws ObjectNotFoundException {

		return this.departmentContext.load(item);
	}

	public Department createDepartment(Department item) {

		return this.departmentContext.create(item);
	}

	public Department deleteDepartment(Department item) {

		return this.departmentContext.delete(item);
	}
}
