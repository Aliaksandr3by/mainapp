package com.example.mainapp;


import com.example.mainapp.controller.EmployeeController;
import com.example.mainapp.exeptions.NotFoundException;
import com.example.mainapp.model.EmployeeContext;
import com.example.mainapp.model.entity.Department;
import com.example.mainapp.model.entity.Employee;
import com.example.mainapp.model.entity.Gender;
import com.example.mainapp.service.EmployeeService;
import com.example.mainapp.service.IService;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MainappApplicationTests {

	@Autowired
	private Logger logger;

	@Autowired
	private SessionFactory sessionFactory;

	private EmployeeController employeeController;

	private Department department1 = new Department(
			1L,"qwe", LocalDateTime.now()
	);

	private Employee employee = new Employee(
			"fn" + Math.random() * 20,
			"ln" + Math.random() * 20,
			department1,
			"jt" + Math.random() * 20,
			Gender.FEMALE
	);

	@Mock
	private Employee employee1;

	//@Before
	@BeforeEach
	public void setUp() {

		EmployeeContext employeeContext = new EmployeeContext(sessionFactory, logger);
		IService<Employee> employeeService = new EmployeeService(employeeContext);
		employeeController = new EmployeeController(employeeService);

		MockitoAnnotations.initMocks(this);

	}

	/**
	 * получение существующего элемента
	 */
	@Test
	public void mustGetEmployeesOnId() {

		Employee t = employeeController.createEmployee(employee1);
		assertNotNull(employeeController.getEmployeeById(t.getEmployeeId()));
		this.employee.setEmployeeId(t.getEmployeeId());
		assertNotNull(employeeController.deleteEmployeeById(this.employee));
	}

	@DisplayName("it gets all employees")
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
		assertThrows(ResponseStatusException.class, () -> employeeController.getEmployeeById(null));
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

		assertThrows(ResponseStatusException.class, () -> employeeController.createEmployee(null));
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
		ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> employeeController.deleteEmployeeById(new Employee()));
		assertTrue(thrown.getMessage().contains("non id"));
	}

}
