package com.example.mainapp.rest;

import com.example.mainapp.DAO.IHibernateUtil;
import com.example.mainapp.DAO.entity.Employee;
import com.example.mainapp.exeptions.NotFoundException;
import com.example.mainapp.service.EmployeeService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/employee")
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
	 * @return
	 */
	@RequestMapping(value = "/employees", method = RequestMethod.GET)
	public List<Employee> getEmployees() {
		try {

			return EmployeeService.getEmployees(this.sessionFactory);

		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}


	/**
	 * элемент по ID
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/employees/{id}", method = RequestMethod.GET)
	public Employee getEmployeeById(@PathVariable Long id) {
		try {

			return EmployeeService.getEmployeeById(this.sessionFactory, id);

		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Обновление по ID
	 * @param employee
	 * @return
	 */
	@RequestMapping(value = "/employees", method = RequestMethod.PUT)
	public boolean updateEmployeeById(@RequestBody Employee employee) {

		try {

			return EmployeeService.updateEmployeeById(this.sessionFactory, employee);

		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * удаление по id
	 * @param employee
	 * @return
	 */
	@RequestMapping(value = "/employees", method = RequestMethod.DELETE)
	public boolean deleteEmployeeById(@RequestBody Employee employee) {
		try {

			return EmployeeService.deleteEmployeeById(this.sessionFactory, employee);

		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Сохранение элемента
	 * @param employee
	 * @return
	 */
	@RequestMapping(value = "/employees", method = RequestMethod.POST)
	public long saveEmployeeById(@RequestBody Employee employee) {

		try {

			return EmployeeService.saveEmployeeById(this.sessionFactory, employee);

		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}


}
