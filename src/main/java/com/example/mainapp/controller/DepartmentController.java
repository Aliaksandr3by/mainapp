package com.example.mainapp.controller;

import com.example.mainapp.exeptions.NotFoundException;
import com.example.mainapp.model.entity.Department;
import com.example.mainapp.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/departments", produces = "application/json")
@CrossOrigin(origins = "*")
public class DepartmentController {

	private IService<Department> departmentService;

	public DepartmentController() {
	}

	@Autowired
	public DepartmentController(IService<Department> service) {
		this.departmentService = service;
	}

	@GetMapping(value = "")
	@ResponseStatus(value = HttpStatus.OK)
	public List<Department> getDepartments() {
		try {

			return departmentService.getAll("idDepartment");

		} catch (Throwable e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}

	@GetMapping(value = "/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public Department getDepartmentById(@PathVariable("id") Long id) {
		try {

			return departmentService.load(new Department(id));

		} catch (Throwable e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}

	@PostMapping(value = "/")
	@ResponseStatus(value = HttpStatus.CREATED)
	public Department saveDepartment(@RequestBody Department item) {
		try {

			return departmentService.create(item);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}

	}

	@DeleteMapping(value = "")
	@ResponseStatus(value = HttpStatus.OK)
	public Department deleteDepartments(@RequestBody Department item) {
		try {

			return departmentService.delete(item);

		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide correct Id", e);
		}
	}

	public static void main(String[] args) {

	}

}
