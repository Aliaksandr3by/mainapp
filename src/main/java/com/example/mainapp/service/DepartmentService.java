package com.example.mainapp.service;

import com.example.mainapp.model.DepartmentContext;
import com.example.mainapp.model.entity.Department;
import lombok.Getter;
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

	public List<Department> getDepartment(Integer id) throws Exception {

		return this.departmentContext.getDepartmentById(id);
	}

	public List<Department> getDepartments() throws Exception {

		return this.departmentContext.getDepartments();
	}

	public Department deleteDepartment(Department item) {

		return this.departmentContext.deleteDepartment(item);
	}
}
