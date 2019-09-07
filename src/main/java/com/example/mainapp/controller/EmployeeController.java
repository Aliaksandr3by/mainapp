package com.example.mainapp.controller;

import com.example.mainapp.configuration.InjectConfiguration;
import com.example.mainapp.exeptions.NotFoundException;
import com.example.mainapp.model.EmployeeSlaveContext;
import com.example.mainapp.model.IEmployeeContext;
import com.example.mainapp.model.entity.Employee;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
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

	@GetMapping(value = "SessionFactory")
	@ResponseStatus(value = HttpStatus.OK)
	public void EmployeeController(String tmp) {
		try {

			ApplicationContext ctx = new AnnotationConfigApplicationContext(InjectConfiguration.class);

			SessionFactory sessionFactory = ctx.getBean("sessionFactory", SessionFactory.class);

			Logger logger = ctx.getBean("LOG", Logger.class);

			logger.info("session");

		} catch (Throwable e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
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
			return (List<Employee>) employeeService.getEmployees("employeeId");

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
