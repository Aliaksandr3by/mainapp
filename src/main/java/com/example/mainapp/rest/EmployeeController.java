package com.example.mainapp.rest;

import com.example.mainapp.DAO.entity.Employee;
import com.example.mainapp.exeptions.NotFoundException;
import com.example.mainapp.service.EmployeeContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "/employees", produces = "application/json")
@CrossOrigin(origins = "*")
public class EmployeeController {

	@Resource(name = "providerEmployeeService")
//	@Qualifier("providerEmployeeService")
	private EmployeeContext<Employee> employeeService;


	public EmployeeController() {
//		ApplicationContext ctx = new AnnotationConfigApplicationContext(EmployeeConfiguration.class);
//		this.employeeService = ctx.getBean("providerEmployeeService", EmployeeService.class);
	}

	/**
	 * Method gets all items
	 * @return
	 */
	@GetMapping(value = "/")
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
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id) {

		Employee tmp = new Employee();
		tmp.setEmployeeId(id);
		Employee employee = employeeService.loadEmployeeById(tmp);

		return new ResponseEntity<>(employee, Objects.nonNull(employee) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}


	/**
	 * Method saves item
	 *
	 * @param employee
	 * @return
	 */
	@PostMapping(value = "/")
	@ResponseStatus(HttpStatus.CREATED)
	public Employee saveEmployee(@RequestBody Employee employee) {

		return employeeService.createEmployee(employee);
	}

	/**
	 * Method completely updates an item
	 *
	 * @param employee
	 * @return
	 */
	@PutMapping(value = "")
	public ResponseEntity<Employee> putEmployeeById(@RequestBody Employee employee) {

		boolean isCreated = Objects.isNull(employee.getEmployeeId());

		Employee tmp = null;

		if (Objects.isNull(tmp = employeeService.updateEmployee(employee))) {
			throw new NotFoundException("error put");
		}

		return new ResponseEntity<>(tmp, isCreated ? HttpStatus.CREATED : HttpStatus.OK);
	}

	/**
	 * Method patch an entity
	 * Need sets an id in body
	 *
	 * @param employee
	 * @return
	 */
	@PatchMapping(value = "")
	public ResponseEntity<Employee> patchPartEmployeeById(@RequestBody Employee employee) {

		Employee tmp = employeeService.patchEmployee(employee);

		return new ResponseEntity<>(tmp, HttpStatus.OK);
	}

	/**
	 * Method deletes an item
	 * @param employee
	 * @return
	 */
	@DeleteMapping(value = "")
	public ResponseEntity<Employee> deleteEmployeeById(@RequestBody Employee employee) {

		Employee tmp = employeeService.deleteEmployee(employee);

		return new ResponseEntity<>(tmp, Objects.nonNull(tmp) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}

}
