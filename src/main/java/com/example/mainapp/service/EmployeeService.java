package com.example.mainapp.service;

import com.example.mainapp.DAO.entity.Employee;
import com.example.mainapp.exeptions.NotFoundException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;

public class EmployeeService {

	public static List<Employee> getEmployees(SessionFactory sessionFactory) {

		try (Session session = sessionFactory.openSession()) {

			return session.createQuery("from Employee", Employee.class).list();
		}
	}

	public static Employee getEmployeeById(SessionFactory sessionFactory, @PathVariable Long id) throws HibernateException {

		//FIXME
// session.load (hibernateLazyInitializer) не работает с try+res и с FAIL_ON_EMPTY_BEANS
//		Session session = sessionFactory.openSession();
//		session.beginTransaction();
//		Employee el = session.load(Employee.class, id);
//		session.getTransaction().commit();
//		return el;

		try (Session session = sessionFactory.openSession()) {

			session.beginTransaction();

//				List<Employee> productNames = session
//						.createQuery("from Employee where employeeId = :employeeId", Employee.class)
//						.setParameter("employeeId", id)
//						.list();

			Employee el = session.get(Employee.class, id);

			session.getTransaction().commit();

			return el;
		}

	}

	public static boolean updateEmployeeById(SessionFactory sessionFactory, Employee employee) throws HibernateException {

		try (Session session = sessionFactory.openSession()) {

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
	}

	public static boolean deleteEmployeeById(SessionFactory sessionFactory, Employee employee) throws NotFoundException, HibernateException {

		if (employee.getEmployeeId() == null) throw new NotFoundException("non id");

		try (Session session = sessionFactory.openSession()) {

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
	}


	public static long saveEmployeeById(SessionFactory sessionFactory, Employee employee) throws HibernateException {

		try (Session session = sessionFactory.openSession()) {

			session.beginTransaction();

			long id = (long) session.save(Objects.requireNonNull(employee));

			session.getTransaction().commit();

			return id;
		}
	}

}
