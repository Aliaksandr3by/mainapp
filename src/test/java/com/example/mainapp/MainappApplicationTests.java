package com.example.mainapp;


import com.example.mainapp.DAO.HibernateUtil;
import com.example.mainapp.DAO.entity.Employee;
import com.example.mainapp.DAO.entity.Gender;
import com.example.mainapp.controller.EmployeeController;
import com.example.mainapp.exeptions.NotFoundException;
import org.hibernate.ObjectNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainappApplicationTests {

	EmployeeController employeeController = new EmployeeController(new HibernateUtil());

	Employee employee = new Employee("test", "test", 1L, "test", Gender.FEMALE);

	@Test(expected = ObjectNotFoundException.class)
	public void mustGetObjectNotFoundException() {
		employeeController.getEmployeesOnId(99L);
	}

	@Test
	public void mustGetNull() {
		assertNull(employeeController.getEmployeesOnId(99L));
	}

	@Test
	public void mustGetEmployeesOnId() {

		assertNotNull(employeeController.getEmployeesOnId(1L));
	}

	@Test
	public void mustGetAllEmployees() {

		assertNotNull(employeeController.gatAllEmployees());
	}

	/**
	 * delete /  save
	 */
	@Test
	public void mustTest() {
		Employee employee = employeeController.getEmployeesOnId(3L);

		if (!Objects.isNull(employee)) {

			assertNotNull(employee);
			assertTrue(employeeController.deleteOneEmployees(employee));

		} else {
			assertTrue(employeeController.saveEmployees(this.employee));
		}

	}

	/**
	 * delete Throw
	 */
	@Test
	public void mustGetNotFoundExceptionsWithDelete() {
		assertThrows(NotFoundException.class, () -> employeeController.deleteOneEmployees(new Employee()));
	}

}
