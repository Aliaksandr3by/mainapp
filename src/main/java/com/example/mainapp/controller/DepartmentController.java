package com.example.mainapp.controller;

import com.example.mainapp.model.DepartmentContext;
import com.example.mainapp.model.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping(path = "/department", produces = "application/json")
@CrossOrigin(origins = "*")
public class DepartmentController {

	private DepartmentContext departmentContext;

	public DepartmentController() {

	}

	@Autowired
	public DepartmentController(@Qualifier("departmentContext") DepartmentContext departmentContext) {
		this.departmentContext = departmentContext;
	}

	@GetMapping(value = "/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public List<Department> getEmployees(@PathVariable("id") Integer id) {
		try {

			return departmentContext.getDepartment(id);

		} catch (Throwable e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}

	public static void main(String[] args) {

	}

}
