package com.example.mainapp.controller;

import com.example.mainapp.DAO.entity.Employee;
import com.example.mainapp.DAO.entity.Gender;
import com.example.mainapp.DAO.IHibernateUtil;
import org.hibernate.Session;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	private IHibernateUtil iHibernateUtil;

	private StandardServiceRegistry serviceRegistryBuilder;

	private final String CON = "hibernate.postgres.employeedb.cfg.xml";

	@Autowired
	public EmployeeController(@Qualifier("hibernateUtil") IHibernateUtil iHibernateUtil) {
		try {

			this.iHibernateUtil = iHibernateUtil;
			if (this.serviceRegistryBuilder == null) {
				this.serviceRegistryBuilder = iHibernateUtil.buidIn(CON);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@RequestMapping(value = "/employees", method = RequestMethod.GET)
	public List<Employee> employees() throws Exception {
		try {

			return iHibernateUtil.setUp(Employee.class, (sessionFactory) -> {

				try (Session session = sessionFactory.openSession()) {

					return session.createQuery("from com.example.mainapp.DAO.entity.Employee")
									.list();

				}

			});

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = "/employees/{id}", method = RequestMethod.GET)
	public List<Employee> employeesId(@PathVariable Long id) throws Exception {
		try {

			return iHibernateUtil.setUp(Employee.class, (sessionFactory) -> {

				try (Session session = sessionFactory.openSession()) {

					List<Employee> productNames =
							session.createQuery("from com.example.mainapp.DAO.entity.Employee")
									.list();

					return productNames
							.stream()
							.filter(e -> e.getEmployeeId() == id)
							.collect(Collectors.toList());
				}

			});

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = "/employees", method = RequestMethod.POST)
	public List<Employee> getEmployees(@RequestBody Employee employee) throws Throwable {

		try {

			return iHibernateUtil.setUp(Employee.class, (sessionFactory) -> {

				Employee tmp = employee;
				if (employee == null) {
					tmp = new Employee("qwe", "qweqwe", 1L, "", Gender.FEMALE);

				}

				try (Session session = sessionFactory.openSession()) {

					session.beginTransaction();
					session.save(tmp);
					session.getTransaction().commit();

					List<Employee> productNames =
							session.createQuery("from com.example.mainapp.DAO.entity.Employee")
									.list();

					return productNames;
				}

			});

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
