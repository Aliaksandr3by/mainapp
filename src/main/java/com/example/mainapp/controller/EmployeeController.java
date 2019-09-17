package com.example.mainapp.controller;

import com.example.mainapp.exeptions.NotFoundException;
import com.example.mainapp.model.IContext;
import com.example.mainapp.model.entity.Employee;
import com.example.mainapp.service.IService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "/employees", produces = "application/json")
@CrossOrigin(origins = "*")
public class EmployeeController {

	private IService<Employee> employeeService;

	public EmployeeController() {
	}

	@Autowired
	public EmployeeController(IService<Employee> service) {
		this.employeeService = service;
	}

	/**
	 * Method gets all items
	 *
	 * @return
	 */
	@GetMapping(value = "")
	@ResponseStatus(value = HttpStatus.OK)
	public List<Employee> getEmployees() {
		try {

			return (List<Employee>) employeeService.findAll("employeeId");

		} catch (Throwable e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
		}
	}

	/**
	 * Method gets all items by id
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public Employee getEmployeeById(@PathVariable("id") Long id) {
		try {

			return employeeService.findById(new Employee(id));

		} catch (ObjectNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e.getCause());
		} catch (Throwable e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
		}
	}


	/**
	 * Method saves item
	 *
	 * @param employee
	 * @return
	 */
	@PostMapping(value = "/")
	@ResponseStatus(value = HttpStatus.CREATED)
	public Employee createEmployee(@RequestBody Employee employee) {
		try {

			return employeeService.insert(employee);

		} catch (Throwable e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
		}

	}

	/**
	 * Method completely updates an item
	 *
	 * @param employee
	 * @return
	 */
	@PutMapping(value = "")
	public ResponseEntity<Employee> putEmployeeById(@RequestBody Employee employee) {
		try {

			boolean isCreated = Objects.isNull(employee.getEmployeeId());

			return new ResponseEntity<>(employeeService.update(employee), isCreated ? HttpStatus.CREATED : HttpStatus.OK);

		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide correct Id", e);
		} catch (Throwable e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
		}
	}

	/**
	 * Method patch an entity
	 * Need sets an id in body
	 *
	 * @param employee
	 * @return
	 */
	@PatchMapping(value = "")
	@ResponseStatus(value = HttpStatus.CREATED)
	public Employee patchPartEmployeeById(@RequestBody Employee employee) {
		try {

			return employeeService.patch(employee);

		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide correct Id", e);
		} catch (Throwable e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
		}
	}

	/**
	 * This method removes the element according to the ID (set in the body)
	 *
	 * @param employee
	 * @return
	 */
	@DeleteMapping(value = "")
	@ResponseStatus(value = HttpStatus.OK)
	public Employee deleteEmployeeById(@RequestBody Employee employee) {
		try {

			return employeeService.delete(employee);

		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide correct Id", e);
		} catch (Throwable e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
		}
	}

	/**
	 * This method removes the element according to the ID
	 *
	 * @param id
	 */
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteEmployeeById(@PathVariable("id") Long id) {
		try {

			employeeService.delete( new Employee(id));

		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide correct Id", e);
		} catch (Throwable e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
		}
	}

}
