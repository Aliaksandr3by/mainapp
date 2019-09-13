package com.example.mainapp.controller;

import com.example.mainapp.model.EmployeeSlaveContext;
import com.example.mainapp.model.entity.EmployeeSlave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
@RequestMapping(path = "/EmployeeSlaves", produces = "application/json")
@CrossOrigin(origins = "*")
public class EmployeeSlavesController {

	private EmployeeSlaveContext employeeSlaveContext;

	public EmployeeSlavesController() {

	}

	@Autowired
	public EmployeeSlavesController(
			@Qualifier("employeeSlaveContext") EmployeeSlaveContext employeeSlaveContext
	) {
		this.employeeSlaveContext = employeeSlaveContext;
	}


	/**
	 * this method return all Employee what have slaves
	 *
	 * @return
	 */
	@GetMapping(value = "EmployeeSlaves")
	@ResponseStatus(value = HttpStatus.OK)
	public Collection<EmployeeSlave> getEmployeeSlaves() {
		try {

			return employeeSlaveContext.findAll("");

		} catch (Throwable e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}

	@PostMapping(value = "/EmployeeSlave")
	@ResponseStatus(value = HttpStatus.CREATED)
	public EmployeeSlave saveEmployeeSlave(@RequestBody EmployeeSlave employeeSlavePK) {
		try {

			return employeeSlaveContext.insert(employeeSlavePK);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}
}
