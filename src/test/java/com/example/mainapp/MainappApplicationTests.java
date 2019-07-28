package com.example.mainapp;


import com.example.mainapp.DAO.HibernateUtil;
import com.example.mainapp.controller.EmployeeController;
import org.hibernate.ObjectNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainappApplicationTests {


	EmployeeController employeeController = new EmployeeController(new HibernateUtil());

	@Test(expected = ObjectNotFoundException.class)
	public void mustGetObjectNotFoundException() {
		employeeController.getEmployeesOnId(99L);
	}

	@Test
	public void mustGetNull() {
		assertNull(employeeController.getEmployeesOnId(99L));
	}

	@Test
	public void mustGetData() {
		assertNotNull(employeeController.getEmployeesOnId(1L));
	}
}
