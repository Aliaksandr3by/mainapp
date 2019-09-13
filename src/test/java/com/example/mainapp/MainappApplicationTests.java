package com.example.mainapp;


import com.example.mainapp.controller.EmployeeController;
import com.example.mainapp.exeptions.NotFoundException;
import com.example.mainapp.model.EmployeeContext;
import com.example.mainapp.model.entity.Employee;
import com.example.mainapp.service.EmployeeService;
import com.example.mainapp.service.IService;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MainappApplicationTests {

	@Autowired
	private Logger logger;

	@Autowired
	private SessionFactory sessionFactory;

	private EmployeeController employeeController;

	@Mock
	private Employee employee;

	//@Before
	@BeforeEach
	public void setUp() {
		EmployeeContext employeeContext = new EmployeeContext(sessionFactory, logger);
		IService<Employee> employeeService = new EmployeeService(employeeContext);
		employeeController = new EmployeeController(employeeService);
		MockitoAnnotations.initMocks(this);
	}

//	private Employee employee = new Employee(
//			"fn" + Math.random() * 20,
//			"ln" + Math.random() * 20,
//			new Department(),
//			"jt" + Math.random() * 20,
//			Gender.FEMALE
//	);

	@DisplayName("Test Mock helloService + helloRepository")
	@Test
	public void mustGetNoNNull() {
		List<Employee> list = employeeController.getEmployees();
		logger.info("assertNotNull");
		assertNotNull(employeeController);
	}


	/**
	 * получение несуществующего элемента
	 */
	@Test
	public void mustGetNull() {

//		assertNull(employeeController.getEmployeeById(9999999L));
		assertThrows(NotFoundException.class, () -> employeeController.getEmployeeById(null));
	}

	/**
	 * получение существующего элемента
	 */
	@Test
	public void mustGetEmployeesOnId() {

		Employee t = employeeController.createEmployee(this.employee);
		assertNotNull(employeeController.getEmployeeById(t.getEmployeeId()));
		this.employee.setEmployeeId(t.getEmployeeId());
		assertNotNull(employeeController.deleteEmployeeById(this.employee));
	}

	/**
	 * получение всех элементов
	 */
	@Test
	public void mustGetAllEmployees() {

		assertNotNull(employeeController.createEmployee(this.employee));
		assertNotNull(employeeController.getEmployees());
		assertTrue(employeeController.getEmployees().size() > 0);
	}

	/**
	 * успешное сохранение
	 */
	@Test
	public void mustSave() {

		assertNotNull(employeeController.createEmployee(this.employee));
	}

	/**
	 * неуспешное сохранение
	 */
	@Test
	public void mustNoSave() {

		assertThrows(NullPointerException.class, () -> employeeController.createEmployee(null));
	}

	/**
	 * успешное удаление
	 */
	@Test
	public void mustDeleted() {

		Employee t = employeeController.createEmployee(this.employee);
		employee.setEmployeeId(t.getEmployeeId());
		assertNotNull(employeeController.deleteEmployeeById(this.employee));
	}

	/**
	 * ошибка удаления Throw
	 */
	@Test
	public void mustGetNotFoundExceptionsWithDelete() {
		NotFoundException thrown = assertThrows(NotFoundException.class, () -> employeeController.deleteEmployeeById(new Employee()));
		assertTrue(thrown.getMessage().contains("non id"));
	}

}
