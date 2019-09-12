package com.example.mainapp.controller;

import com.example.mainapp.exeptions.NotFoundException;
import com.example.mainapp.model.entity.Department;
import com.example.mainapp.model.entity.Employee;
import com.example.mainapp.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping(path = "/departments", produces = "application/json")
@CrossOrigin(origins = "*")
public class DepartmentController {

	private DepartmentService departmentService;

	public DepartmentController() {
	}

	@Autowired
	public DepartmentController(
			@Qualifier("departmentService") DepartmentService departmentService
	) {
		this.departmentService = departmentService;
	}

	@GetMapping(value = "")
	@ResponseStatus(value = HttpStatus.OK)
	public List<Department> getDepartments() {
		try {

			return departmentService.getDepartments("idDepartment");

		} catch (Throwable e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}

	@GetMapping(value = "/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public Department getDepartmentById(@PathVariable("id") Long id) {
		try {

			return departmentService.getDepartment(new Department(id));

		} catch (Throwable e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}

	@PostMapping(value = "/")
	@ResponseStatus(value = HttpStatus.CREATED)
	public Department saveDepartment(@RequestBody Department item) {
		try {

			return departmentService.createDepartment(item);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}

	}

	@DeleteMapping(value = "")
	@ResponseStatus(value = HttpStatus.OK)
	public Department deleteDepartments(@RequestBody Department item) {
		try {

			return departmentService.deleteDepartment(item);

		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide correct Id", e);
		}
	}

	public static void main(String[] args) {

	}

}
