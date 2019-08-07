package com.example.mainapp.service;

import com.example.mainapp.DAO.entity.Employee;
import com.example.mainapp.exeptions.NotFoundException;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

public class EmployeeService<T extends Employee> implements IEmployeeService<T> {

	private final Class<T> typeParameterClass;
	private final SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public EmployeeService(Class<T> typeParameterClass, SessionFactory sessionFactory) {
		this.typeParameterClass = typeParameterClass;
		this.sessionFactory = sessionFactory;
	}

	public List<T> getEmployees() {

		try (Session session = this.sessionFactory.openSession()) {

			return session.createQuery("from Employee e", typeParameterClass).list();
		}
	}

	public T getEmployeeById(Long id) throws ObjectNotFoundException {

		if (id == null) throw new NotFoundException("non id");

		try (Session session = this.sessionFactory.openSession()) {

			return session.get(typeParameterClass, id);
		}
	}

	public T saveEmployeeById(T employee) {

		try (Session session = this.sessionFactory.openSession()) {

			try {

				session.beginTransaction();

				long id = (long) session.save(Objects.requireNonNull(employee));

				session.getTransaction().commit();

				return session.get(typeParameterClass, id);

			} catch (Exception e) {
				if (session.getTransaction().isActive()) {
					session.getTransaction().rollback();
					e.printStackTrace();
				}
				throw e;
			}
		}
	}

	/**
	 * add or update if there is no ID
	 *
	 * @param employee
	 * @return
	 * @throws HibernateException
	 */
	public boolean putEmployeeById(@NotNull T employee) {

		boolean isCreated = false;

		try (Session session = this.sessionFactory.openSession()) {

			try {

				isCreated = Objects.isNull(employee.getEmployeeId());

				session.beginTransaction();

				session.saveOrUpdate(Objects.requireNonNull(employee));

				session.getTransaction().commit();

				if (employee.getEmployeeId() == null) throw new NotFoundException("non id");

				return isCreated;

			} catch (Exception e) {
				if (session.getTransaction().isActive()) {
					session.getTransaction().rollback();
					e.printStackTrace();
				}
				throw e;
			}
		}
	}

	public boolean patchEmployeeById(T employee) throws NotFoundException {

		if (employee.getEmployeeId() == null) throw new NotFoundException("non id");
		if (employee.IsEmpty(employee)) throw new NotFoundException("is empty");

		try (Session session = this.sessionFactory.openSession()) {

			try {
				session.beginTransaction();

				Employee tmp = session.get(typeParameterClass, employee.getEmployeeId());
				tmp.employeeUpdater(employee);

				session.update(Objects.requireNonNull(tmp));

				session.getTransaction().commit();

				return true;

			} catch (Exception e) {
				if (session.getTransaction().isActive()) {
					session.getTransaction().rollback();
					e.printStackTrace();
				}
				throw e;
			}
		}
	}

	public boolean deleteEmployeeById(T employee) throws NotFoundException {

		if (employee.getEmployeeId() == null) throw new NotFoundException("non id");

		try (Session session = this.sessionFactory.openSession()) {

			try {

				session.beginTransaction();

				if (!Objects.isNull(session.load(typeParameterClass, employee.getEmployeeId()))) {
					session.delete(employee);
				}

				session.getTransaction().commit();

				return true;

			} catch (Exception e) {
				if (session.getTransaction().isActive()) {
					session.getTransaction().rollback();
					e.printStackTrace();
				}
				throw e;
			}
		}
	}

}
