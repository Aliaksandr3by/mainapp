package com.example.mainapp.rest;

import com.example.mainapp.DAO.IHibernateUtil;
import com.example.mainapp.DAO.entity.Employee;
import com.example.mainapp.exeptions.NotFoundException;
import com.example.mainapp.service.EmployeeService;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.logging.ErrorManager;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/employees", produces = "application/json")
@CrossOrigin(origins = "*")
public class EmployeeController {

	private IHibernateUtil iHibernateUtil;

	private StandardServiceRegistry serviceRegistryBuilder;
	private SessionFactory sessionFactory;

	@Autowired
	public EmployeeController(@Qualifier("hibernateUtil") IHibernateUtil iHibernateUtil) {
		try {

			this.iHibernateUtil = iHibernateUtil;
			iHibernateUtil.buidIn("hibernate.postgres.employeedb.cfg.xml", Employee.class);
			this.serviceRegistryBuilder = this.iHibernateUtil.getServiceRegistryBuilder();
			this.sessionFactory = this.iHibernateUtil.getSessionFactory();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Все элементы
	 *
	 * @return
	 */
	@GetMapping(value = "")
	public List<Employee> getEmployees() {
		try {

			return EmployeeService.getEmployees(this.sessionFactory).stream().collect(Collectors.toList());

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

			Employee employee = EmployeeService.getEmployeeById(this.sessionFactory, id);

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
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public Employee saveEmployee(@RequestBody Employee employee) {

		try {

			return EmployeeService.saveEmployeeById(this.sessionFactory, employee);

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
	@PutMapping(value = "")
	public ResponseEntity<Employee> putEmployeeById(@RequestBody Employee employee) {

		try {

			if (!EmployeeService.putEmployeeById(this.sessionFactory, employee)) {
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

			if (!EmployeeService.putEmployeeById(this.sessionFactory, employee)) {
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
	@PatchMapping(value = "")
	public boolean patchPartEmployeeById(@RequestBody Employee employee) {

		try {

			return EmployeeService.patchEmployeeById(this.sessionFactory, employee);

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
			return EmployeeService.patchEmployeeById(this.sessionFactory, employee);

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
			Employee tnp = new Employee();
			tnp.setEmployeeId(id);

			//не работает(
//			Employee tmp = new Employee(){{
//				setEmployeeId(id);
//			}};

			return EmployeeService.deleteEmployeeById(this.sessionFactory, tnp);

		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@DeleteMapping(value = "/")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public boolean deleteEmployeeById(@RequestBody Employee employee) {
		try {

			return EmployeeService.deleteEmployeeById(this.sessionFactory, employee);

		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

}
