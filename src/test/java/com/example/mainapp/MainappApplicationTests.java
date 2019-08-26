package com.example.mainapp;


import com.example.mainapp.DAO.entity.Department;
import com.example.mainapp.DAO.entity.Employee;
import com.example.mainapp.DAO.entity.Gender;
import com.example.mainapp.exeptions.NotFoundException;
import com.example.mainapp.rest.EmployeeController;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

class TestThread implements Runnable {

	public static int count = 0;

	EmployeeController employeeController;
	Employee employee;
	Thread tmp;

	public TestThread(EmployeeController employeeController, Employee employee) {
		this.employeeController = employeeController;
		this.employee = employee;
		tmp = new Thread(this);
		tmp.start();
	}

	@Override
	public void run() {
		try {
			++count;
			Thread.currentThread().sleep((long) Math.ceil(Math.random() * 5000));
			this.employeeController.saveEmployee(employee);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class MainappApplicationTests {

	private EmployeeController employeeController = new EmployeeController();

	private Employee employee = new Employee(
			"fn" + Math.random() * 20,
			"ln" + Math.random() * 20,
			new Department(),
			"jt" + Math.random() * 20,
			Gender.FEMALE);

	//FIXME
//	@Test(expected = ObjectNotFoundException.class)
//	public void mustGetObjectNotFoundException() {
//		employeeController.getEmployeeById(999999L);
//	}

	@Test
	public void voidThread() throws InterruptedException {

		for (int i = 0; i < 10; i++) {
			TestThread tr = new TestThread(
					new EmployeeController(),
					new Employee(
							TestThread.count + "fn" + Math.floor(Math.random() * 3),
							TestThread.count + "ln" + Math.ceil(Math.random() * 3),
							new Department(),
							i + "jt" + (Math.random() * 3),
							Gender.FEMALE));
		}
		System.out.println(TestThread.count);
		Thread.currentThread().join(7000);
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

		Employee t = employeeController.saveEmployee(this.employee);
		assertNotNull(employeeController.getEmployeeById(t.getEmployeeId()));
		this.employee.setEmployeeId(t.getEmployeeId());
		assertNotNull(employeeController.deleteEmployeeById(this.employee));
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
