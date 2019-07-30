package com.example.mainapp.service;

import com.example.mainapp.DAO.entity.Employee;
import com.example.mainapp.exeptions.NotFoundException;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.Transient;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Objects;

public class EmployeeService {

	public static List<Employee> getEmployees(SessionFactory sessionFactory) {

		try (Session session = sessionFactory.openSession()) {

			return session.createQuery("from Employee", Employee.class).list();
		}
	}

	public static Employee getEmployeeById(SessionFactory sessionFactory, Long id) throws ObjectNotFoundException {

		if (id == null) throw new NotFoundException("non id");

		try (Session session = sessionFactory.openSession()) {

			session.beginTransaction();

//				List<Employee> productNames = session
//						.createQuery("from Employee where employeeId = :employeeId", Employee.class)
//						.setParameter("employeeId", id)
//						.list();


//			Employee el = session.get(Employee.class, id);
			Employee el = Hibernate.unproxy(session.load(Employee.class, id), Employee.class);

			session.getTransaction().commit();

			return el;
		}

	}

	/**
	 * add or update if there is no ID
	 *
	 * @param sessionFactory
	 * @param employee
	 * @return
	 * @throws HibernateException
	 */
	public static boolean putEmployeeById(SessionFactory sessionFactory, Employee employee) {

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

			session.saveOrUpdate(Objects.requireNonNull(employee));

			session.getTransaction().commit();

			return session.get(Employee.class, employee.getEmployeeId()).equals(employee);
		}
	}

	public static boolean patchEmployeeById(SessionFactory sessionFactory, Employee employee) throws NotFoundException {

		if (employee.getEmployeeId() == null) throw new NotFoundException("non id");
		if (employee.IsEmpty()) throw new NotFoundException("is empty");

		try (Session session = sessionFactory.openSession()) {

			session.beginTransaction();

			Employee tmp = EmployeeUpdater(session.get(Employee.class, employee.getEmployeeId()), employee);

			session.update(Objects.requireNonNull(tmp));

			session.getTransaction().commit();

			return true;
		}
	}

	private static Employee EmployeeUpdater(Employee old, Employee update) {

		if (old.equals(update)) throw new NotFoundException("Object is equals "); //XXX

		if (update.getFirstName() != null) old.setFirstName(update.getFirstName());
		if (update.getLastName() != null) old.setLastName(update.getLastName());
		if (update.getGender() != null) old.setGender(update.getGender());
		if (update.getJobTitle() != null) old.setJobTitle(update.getJobTitle());
		if (update.getDepartmentId() != null) old.setDepartmentId(update.getDepartmentId());

		return old;
	}

	public static boolean deleteEmployeeById(SessionFactory sessionFactory, Employee employee) throws NotFoundException {

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


	public static long saveEmployeeById(SessionFactory sessionFactory, Employee employee) {

		try (Session session = sessionFactory.openSession()) {

			session.beginTransaction();

			long id = (long) session.save(Objects.requireNonNull(employee));

			session.getTransaction().commit();

			return id;
		}
	}

}
