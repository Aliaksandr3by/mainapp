package com.example.mainapp.rest;

import com.example.mainapp.exeptions.NotFoundException;
import com.example.mainapp.model.entity.Employee;
import com.example.mainapp.service.IEmployeeService;
import org.hibernate.ObjectNotFoundException;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "/employees", produces = "application/json")
@CrossOrigin(origins = "*")
public class EmployeeController {

	@Resource(name = "providerEmployeeService")
//	@Qualifier("providerEmployeeService")
	private IEmployeeService<Employee> employeeService;


	public EmployeeController() {
//		ApplicationContext ctx = new AnnotationConfigApplicationContext(EmployeeConfiguration.class);
//		this.employeeService = ctx.getBean("providerEmployeeService", EmployeeService.class);
	}

	/**
	 * Method gets all items
	 *
	 * @return
	 */
	@GetMapping(value = "")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<Employee> getEmployees() {
		List<Employee> tmp = (List<Employee>) employeeService.getEmployees("employeeId")
//					.stream()
//					.sorted(Comparator.comparing(Employee::getFirstName).thenComparing(Comparator.comparing(Employee::getLastName)))
//					.peek(e -> System.out.println(e))
//					.collect(Collectors.toList())
				;

		return tmp;
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
