package com.example.mainapp;


import com.example.mainapp.DAO.HibernateUtil;
import com.example.mainapp.DAO.entity.Employee;
import com.example.mainapp.DAO.entity.Gender;
import com.example.mainapp.exeptions.NotFoundException;
import com.example.mainapp.rest.EmployeeController;
import org.hibernate.ObjectNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainappApplicationTests {

	private EmployeeController employeeController = new EmployeeController(new HibernateUtil());

	private Employee employee = new Employee("test", "test", 1L, "test", Gender.FEMALE);

	//FIXME
//	@Test(expected = ObjectNotFoundException.class)
//	public void mustGetObjectNotFoundException() {
//		employeeController.getEmployeeById(999999L);
//	}

	/**
	 * получение несуществующего элемента
	 */
	@Test
	public void mustGetNull() {

//		assertNull(employeeController.getEmployeeById(9999999L));
		assertThrows(ObjectNotFoundException.class, () -> employeeController.getEmployeeById(-1L));
	}

	/**
	 * получение существующего элемента
	 */
	@Test
	public void mustGetEmployeesOnId() {

		Employee t = employeeController.saveEmployee(this.employee);
		assertNotNull(employeeController.getEmployeeById(t.getEmployeeId()));
		this.employee.setEmployeeId(t.getEmployeeId());
		assertTrue(employeeController.deleteEmployeeById(this.employee));
	}

	/**
	 * получение всех элементов
	 */
	@Test
	public void mustGetAllEmployees() {

		assertNotNull(employeeController.saveEmployee(this.employee));
		assertNotNull(employeeController.getEmployees());
		assertTrue(employeeController.getEmployees().size() > 0);
	}

	/**
	 * успешное сохранение
	 */
	@Test
	public void mustSave() {

		assertNotNull(employeeController.saveEmployee(this.employee));
	}

	/**
	 * неуспешное сохранение
	 */
	@Test
	public void mustNoSave() {

		assertThrows(NullPointerException.class, () -> employeeController.saveEmployee(null));
	}

	/**
	 * успешное удаление
	 */
	@Test
	public void mustDeleted() {

		Employee t = employeeController.saveEmployee(this.employee);
		employee.setEmployeeId(t.getEmployeeId());
		assertTrue(employeeController.deleteEmployeeById(this.employee));
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
