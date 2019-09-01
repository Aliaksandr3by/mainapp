package com.example.mainapp.controller;

import com.example.mainapp.exeptions.NotFoundException;
import com.example.mainapp.model.EmployeeSlaveContext;
import com.example.mainapp.model.IEmployeeContext;
import com.example.mainapp.model.entity.Employee;
import com.example.mainapp.model.entity.EmployeeSlave;
import com.example.mainapp.model.entity.EmployeeSlavePK;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/employees", produces = "application/json")
@CrossOrigin(origins = "*")
public class EmployeeController {

	//	@Resource(name = "providerEmployeeConfiguration")
//	@Autowired
//	@Qualifier("providerEmployee")
//	@Inject
//	@Named("employeeContext")
	private IEmployeeContext<Employee> employeeService;

	//			@Resource(name = "providerEmployeeSlaveConfiguration")
//	@Autowired
//	@Qualifier(value = "providerEmployeeSlave")
//	@Inject
//	@Named("employeeSlaveContext")
	private EmployeeSlaveContext employeeSlaveService;

	public EmployeeController() {

	}

	@Autowired
	public EmployeeController(
			@Qualifier("employeeComponent") IEmployeeContext<Employee> employeeContext,
			@Qualifier("employeeSlaveContext") EmployeeSlaveContext employeeSlaveContext
	) {
		this.employeeService = employeeContext;
		this.employeeSlaveService = employeeSlaveContext;
	}

//	public EmployeeController() {
//		ApplicationContext ctx = new AnnotationConfigApplicationContext(EmployeeConfiguration.class);
//		this.employeeService = ctx.getBean("providerEmployeeService", EmployeeService.class);
//	}

	/**
	 * Method gets all items
	 *
	 * @return
	 */
	@GetMapping(value = "")
	@ResponseStatus(value = HttpStatus.OK)
	public List<Employee> getEmployees() {
		try {
			return (List<Employee>) employeeService.getEmployees("employeeId");

		} catch (Throwable e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}

	/**
	 * this method return all Employee what have slaves
	 *
	 * @return
	 */
	@GetMapping(value = "EmployeeSlaves")
	@ResponseStatus(value = HttpStatus.OK)
	public Collection<Employee> getEmployeeSlaves() {
		try {
			return employeeService.getEmployees("employeeId")
					.stream()
					.filter(e -> !e.getSlaves().isEmpty())
//				.map((e) -> e.getSlaves())
					.collect(Collectors.toList());

		} catch (Throwable e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
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

			Employee tmp = new Employee();
			tmp.setEmployeeId(id);

			return employeeService.loadEmployeeById(tmp);

		} catch (ObjectNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Object Not Found Provide correct Id", e);
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
	public Employee saveEmployee(@RequestBody Employee employee) {
		try {

			return employeeService.createEmployee(employee);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}

	}

	@PostMapping(value = "/EmployeeSlave")
	@ResponseStatus(value = HttpStatus.CREATED)
	public EmployeeSlave saveEmployeeSlave(@RequestBody EmployeeSlavePK employeeSlavePK) {
		try {

			return employeeSlaveService.createEmployeeSlave(employeeSlavePK);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
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

			return new ResponseEntity<>(employeeService.updateEmployee(employee), isCreated ? HttpStatus.CREATED : HttpStatus.OK);

		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide correct Id", e);
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

			return employeeService.patchEmployee(employee);

		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide correct Id", e);
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

			return employeeService.deleteEmployee(employee);

		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide correct Id", e);
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

			Employee employee = new Employee();
			employee.setEmployeeId(id);
			employeeService.deleteEmployee(employee);

		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide correct Id", e);
		}
	}

}
