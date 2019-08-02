package com.example.mainapp.service;

import com.example.mainapp.DAO.entity.Employee;
import com.example.mainapp.exeptions.NotFoundException;
import org.hibernate.*;

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

			Employee el = session.get(Employee.class, id);
//			Employee el = Hibernate.unproxy(session.load(Employee.class, id), Employee.class);

			session.getTransaction().commit();

			return el;
		}

	}

	public static Employee saveEmployeeById(SessionFactory sessionFactory, Employee employee) {

		try (Session session = sessionFactory.openSession()) {

			session.beginTransaction();

			long id = (long) session.save(Objects.requireNonNull(employee));

			session.getTransaction().commit();

			return session.get(Employee.class, id);
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

		boolean isCreated = false;

		try (Session session = sessionFactory.openSession()) {

			isCreated = Objects.isNull(employee.getEmployeeId());

			session.beginTransaction();

			session.saveOrUpdate(Objects.requireNonNull(employee));

			session.getTransaction().commit();

			if (employee.getEmployeeId() == null) throw new NotFoundException("non id");

			return isCreated;
		}
	}

	public static boolean patchEmployeeById(SessionFactory sessionFactory, Employee employee) throws NotFoundException {

		if (employee.getEmployeeId() == null) throw new NotFoundException("non id");
		if (IsEmpty(employee)) throw new NotFoundException("is empty");

		try (Session session = sessionFactory.openSession()) {

			session.beginTransaction();

			Employee tmp = EmployeeUpdater(session.get(Employee.class, employee.getEmployeeId()), employee);

			session.update(Objects.requireNonNull(tmp));

			session.getTransaction().commit();

			return true;
		}
	}

	public static boolean IsEmpty(Employee tmp) {

		if (tmp.getFirstName() == null
				&& tmp.getLastName() == null
				&& tmp.getGender() == null
				&& tmp.getJobTitle() == null
				&& tmp.getDepartmentId() == null
		) {
			return true;
		}

		return false;
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

			if (!Objects.isNull(session.load(Employee.class, employee.getEmployeeId()))) {
				session.delete(employee);
			}

			session.getTransaction().commit();

			return true;
		}
	}

}
