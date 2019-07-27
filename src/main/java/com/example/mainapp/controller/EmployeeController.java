package com.example.mainapp.controller;

import com.example.mainapp.DAO.entity.Employee;
import com.example.mainapp.DAO.entity.Gender;
import com.example.mainapp.DAO.IHibernateUtil;
import com.example.mainapp.exeptions.NotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	private IHibernateUtil iHibernateUtil;

	private StandardServiceRegistry serviceRegistryBuilder;
	private SessionFactory sessionFactory;

	private final String CON = "hibernate.postgres.employeedb.cfg.xml";

	@Autowired
	public EmployeeController(@Qualifier("hibernateUtil") IHibernateUtil iHibernateUtil) {
		try {

			this.iHibernateUtil = iHibernateUtil;
			iHibernateUtil.buidIn(CON, Employee.class);
			this.serviceRegistryBuilder = this.iHibernateUtil.getServiceRegistryBuilder();
			this.sessionFactory = this.iHibernateUtil.getSessionFactory();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@RequestMapping(value = "/employees", method = RequestMethod.GET)
	public List<Employee> gatAllEmployees() throws Exception {
		try {

			return iHibernateUtil.setUp(Employee.class, (sessionFactory) -> {

				try (Session session = sessionFactory.openSession()) {

					session.beginTransaction();
//					session.
					session.getTransaction().commit();

					return session.createQuery("from Employee", Employee.class).list();

				}

			});

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}


	@RequestMapping(value = "/employees", method = RequestMethod.PUT)
	public boolean updateEmployeesOnId(@RequestBody Employee employee) throws Exception {

		try {

			try (Session session = this.sessionFactory.openSession()) {
//					List<Employee> productNames =
//							session.createQuery("from com.example.mainapp.DAO.entity.Employee", Employee.class)
//									.list();

				session.beginTransaction();
				session.update(Objects.requireNonNull(employee));
				session.getTransaction().commit();

				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = "/employees", method = RequestMethod.DELETE)
	public boolean deleteEmployeesOnId(@RequestBody Employee employee) throws Exception {
		try {

			try (Session session = this.sessionFactory.openSession()) {

				long id = Objects.requireNonNull(employee).getEmployeeId();

				session.beginTransaction();
				session.delete(Objects.requireNonNull(employee));
				session.getTransaction().commit();

				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = "/employees/{id}", method = RequestMethod.GET)
	public List<Employee> getEmployeesOnId(@PathVariable Long id) throws Exception {
		try {

			return iHibernateUtil.setUp(Employee.class, (sessionFactory) -> {

				try (Session session = sessionFactory.openSession()) {

//					List<Employee> productNames = session
//							.createQuery("from Employee as Employee where Employee.employeeId = " + id, Employee.class)
//							.list();

					session.beginTransaction();
					Employee employee = session.get(Employee.class, id);
					session.getTransaction().commit();

					return new ArrayList<Employee>() {
						{
							add(employee);
						}
					};
				}

			});

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = "/employees", method = RequestMethod.POST)
	public boolean saveEmployees(@RequestBody Employee employee) throws Throwable {

		try {

			try (Session session = this.sessionFactory.openSession()) {

				session.beginTransaction();
				session.save(Objects.requireNonNull(employee));
				session.getTransaction().commit();

//				List<Employee> productNames =
//						session.createQuery("from Employee", Employee.class)
//								.list();

				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
