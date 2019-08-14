package com.example.mainapp.rest;

import com.example.mainapp.DAO.IHibernateUtil;
import com.example.mainapp.DAO.entity.Employee;
import com.example.mainapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "/employees", produces = "application/json")
@CrossOrigin(origins = "*")
public class EmployeeController {

	private EmployeeService<Employee> employeeService;
	private IHibernateUtil hibernateUtil;

	@Autowired
	public EmployeeController(@Qualifier("hibernateUtil") IHibernateUtil iHibernateUtil) {
		try {

			this.hibernateUtil = iHibernateUtil;
			this.hibernateUtil.buidIn("hibernate.cfg.xml", Employee.class);

			this.employeeService = new EmployeeService<>(Employee.class, this.hibernateUtil.getSessionFactory());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Все элементы
	 *
	 * @return
	 */
	@GetMapping(value = "/")
	public List<Employee> getEmployees() {
		try {

			List<Employee> tmp = employeeService.getEmployees("employeeId")
//					.stream()
//					.sorted(Comparator.comparing(Employee::getFirstName).thenComparing(Comparator.comparing(Employee::getLastName)))
//					.peek(e -> System.out.println(e))
//					.collect(Collectors.toList())
					;

			return tmp;

		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}


	/**
	 * элемент по ID
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id) {
		try {

			Employee employee = employeeService.getEmployeeById(id);

			if (!Objects.isNull(employee)) {
				return new ResponseEntity<>(employee, HttpStatus.OK);
			}
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
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

		try {

			return employeeService.saveEmployeeById(employee);

		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Полное Обновление по ID или добавление при отсутствии ID
	 *
	 * @param employee
	 * @return
	 */
	@PutMapping(value = "/")
	public ResponseEntity<Employee> putEmployeeById(@RequestBody Employee employee) {

		try {

			if (!employeeService.putEmployeeById(employee)) {
				return new ResponseEntity<>(employee, HttpStatus.OK);
			}
			return new ResponseEntity<>(null, HttpStatus.CREATED);

		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@PutMapping(value = "/{employeeId}")
	public ResponseEntity<Employee> putEmployeeByGetId(@PathVariable("employeeId") Long id, @RequestBody Employee employee) {

		try {

			employee.setEmployeeId(id);

			if (!employeeService.putEmployeeById(employee)) {
				return new ResponseEntity<>(employee, HttpStatus.OK);
			}
			return new ResponseEntity<>(null, HttpStatus.CREATED);

		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Частичное Обновление по ID
	 * при указании в теле
	 *
	 * @param employee
	 * @return
	 */
	@PatchMapping(value = "/")
	public boolean patchPartEmployeeById(@RequestBody Employee employee) {

		try {

			return employeeService.patchEmployeeById(employee);

		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	//при указании в запросе
	@PatchMapping(value = "/{employeeId}")
	public boolean patchPartEmployeeByGetId(@PathVariable("employeeId") Long id, @RequestBody Employee employee) {

		try {

			employee.setEmployeeId(id);
			return employeeService.patchEmployeeById(employee);

		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * удаление по id
	 *
	 * @return
	 */
	@DeleteMapping(value = "/{employeeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public boolean deleteEmployeeById(@PathVariable("employeeId") Long id) {
		try {
			Employee tmp = new Employee();
			tmp.setEmployeeId(id);

			return employeeService.deleteEmployeeById(tmp);

		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@DeleteMapping(value = "/")
	@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "object was deleted")
	public boolean deleteEmployeeById(@RequestBody Employee employee) {
		try {

			return employeeService.deleteEmployeeById(employee);

		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

}
