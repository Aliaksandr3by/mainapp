package com.example.mainapp.rest;

import com.example.mainapp.DAO.entity.Employee;
import com.example.mainapp.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Qualifier;
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
	private IEmployeeService<Employee> employeeService;


	public EmployeeController() {
//		ApplicationContext ctx = new AnnotationConfigApplicationContext(EmployeeConfiguration.class);
//		this.employeeService = ctx.getBean("providerEmployeeService", EmployeeService.class);
	}

//	@Autowired
//	public EmployeeController(
//			@Qualifier("hibernateUtil") IHibernateUtil iHibernateUtil,
//			@Qualifier("employeeService") IEmployeeService employeeService
//	) {
//		this.employeeService = employeeService;
//		this.employeeService.setTypeParameterClass(Employee.class);
//		this.employeeService.setSessionFactory(iHibernateUtil.buildIn("hibernate.employeedb.cfg.xml"));
//	}

	/**
	 * Все элементы
	 *
	 * @return
	 */
	@GetMapping(value = "/")
	public List<Employee> getEmployees() {
		List<Employee> tmp = employeeService.getEmployees("employeeId")
//					.stream()
//					.sorted(Comparator.comparing(Employee::getFirstName).thenComparing(Comparator.comparing(Employee::getLastName)))
//					.peek(e -> System.out.println(e))
//					.collect(Collectors.toList())
				;

		return tmp;
	}


	/**
	 * элемент по ID
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id) {
		Employee employee = employeeService.getEmployeeById(id);

		if (!Objects.isNull(employee)) {
			return new ResponseEntity<>(employee, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}


	/**
	 * Сохранение элемента
	 *
	 * @param employee
	 * @return
	 */
	@PostMapping(value = "/")
	@ResponseStatus(HttpStatus.CREATED)
	public Employee saveEmployee(@RequestBody Employee employee) {

		return employeeService.saveEmployeeById(employee);
	}

	/**
	 * Полное Обновление по ID или добавление при отсутствии ID
	 *
	 * @param employee
	 * @return
	 */
	@PutMapping(value = "")
	public ResponseEntity<Employee> putEmployeeById(@RequestBody Employee employee) {

		boolean isCreated = Objects.isNull(employee.getEmployeeId());

		Employee tmp = null;

		if (Objects.nonNull(tmp = employeeService.putEmployeeById(employee))) {
			return new ResponseEntity<>(tmp, isCreated ? HttpStatus.CREATED : HttpStatus.OK);
		}

		return new ResponseEntity<>(null, HttpStatus.CREATED);
	}

	/**
	 * Частичное Обновление по ID
	 * при указании в теле
	 *
	 * @param employee
	 * @return
	 */
	@PatchMapping(value = "")
	public ResponseEntity<Employee> patchPartEmployeeById(@RequestBody Employee employee) {

		Employee tmp = employeeService.patchEmployeeById(employee);

		return new ResponseEntity<>(tmp, HttpStatus.OK);
	}

	@DeleteMapping(value = "")
	@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "object was deleted")
	public boolean deleteEmployeeById(@RequestBody Employee employee) {

		return employeeService.deleteEmployeeById(employee);
	}

}
