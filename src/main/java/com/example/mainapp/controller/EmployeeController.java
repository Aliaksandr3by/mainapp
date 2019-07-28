package com.example.mainapp.controller;

import com.example.mainapp.DAO.IHibernateUtil;
import com.example.mainapp.DAO.entity.Employee;
import com.example.mainapp.exeptions.NotFoundException;
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

	@RequestMapping(value = "/employees", method = RequestMethod.GET)
	public List<Employee> getEmployees() throws RuntimeException {
		try {

			try (Session session = this.sessionFactory.openSession()) {

				return session.createQuery("from Employee", Employee.class).list();
			}

		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}


	@RequestMapping(value = "/employees/{id}", method = RequestMethod.GET)
	public Employee getEmployeeById(@PathVariable Long id) throws RuntimeException {
		try {

			try (Session session = this.sessionFactory.openSession()) {

				session.beginTransaction();

//				List<Employee> productNames = session
//						.createQuery("from Employee where employeeId = :employeeId", Employee.class)
//						.setParameter("employeeId", id)
//						.list();

				Employee el = session.get(Employee.class, id);
				session.getTransaction().commit();

				/**
				 *     "error": "Internal Server Error",
				 *     "message": "Type definition error: [simple type, class org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor];
				 *     nested exception is com.fasterxml.jackson.databind.exc.InvalidDefinitionException: No serializer found for class
				 *     org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor and no properties discovered to create BeanSerializer
				 *     (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS) (through reference chain:
				 *     com.example.mainapp.controller.EmployeeController$1[0]->
				 *     com.example.mainapp.DAO.entity.Employee$HibernateProxy$1b4NmoQy[\"hibernateLazyInitializer\"])",
				 */
//				Employee employee = session.load(Employee.class, id);

//				return new ArrayList<Employee>() {
//					{
//						add(employee);
//					}
//				};


				return el;
			}

		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = "/employees", method = RequestMethod.PUT)
	public boolean updateEmployeeById(@RequestBody Employee employee) throws RuntimeException {

		try {

			try (Session session = this.sessionFactory.openSession()) {

				session.beginTransaction();

//				String hql = "UPDATE Employee SET " +
//						"firstName = :firstName, " +
//						"lastName = :lastName," +
//						"gender = :gender," +
//						"jobTitle = :jobTitle," +
//						"departmentId = :departmentId " +
//						"where employeeId = :employeeId";
//
//				Query query = session.createQuery(hql);
//
//				query
//						.setParameter("employeeId", employee.getEmployeeId())
//						.setParameter("departmentId", employee.getDepartmentId())
//						.setParameter("firstName", employee.getFirstName())
//						.setParameter("lastName", employee.getLastName())
//						.setParameter("gender", employee.getGender())
//						.setParameter("jobTitle", employee.getJobTitle())
//				;
//
//				int rows = query.executeUpdate();

				session.update(Objects.requireNonNull(employee));

				session.getTransaction().commit();

				return session.get(Employee.class, employee.getEmployeeId()).equals(employee);
			}

		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = "/employees", method = RequestMethod.DELETE)
	public boolean deleteEmployeeById(@RequestBody Employee employee) throws RuntimeException {
		try {

			if (employee.getEmployeeId() == null) throw new NotFoundException("non id");

			try (Session session = this.sessionFactory.openSession()) {

				session.beginTransaction();

//				Query query = session.createQuery("DELETE Employee where employeeId = :eId");
//				query.setParameter("eId", employee.getEmployeeId());
//				int rows = query.executeUpdate();

				if (!Objects.isNull(session.load(Employee.class, employee.getEmployeeId()))) {
					session.delete(employee);
				}

				session.getTransaction().commit();

				return true;
			}

		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = "/employees", method = RequestMethod.POST)
	public long saveEmployeeById(@RequestBody Employee employee) throws RuntimeException {

		try {

			try (Session session = this.sessionFactory.openSession()) {

				session.beginTransaction();

				long id = (long) session.save(Objects.requireNonNull(employee));

				session.getTransaction().commit();

				return id;
			}

		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

}
