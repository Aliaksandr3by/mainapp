package com.example.mainapp.service;

import com.example.mainapp.DAO.entity.Employee;
import com.example.mainapp.exeptions.NotFoundException;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;


@Component
@Qualifier(value = "employeeService")
public class EmployeeService<T extends Employee> implements IEmployeeService<T> {

	private SessionFactory sessionFactory;

	private Class<T> typeParameterClass;

	@Resource(name = "logger")
	private Logger logger;

	public void setTypeParameterClass(Class<T> typeParameterClass) {
		this.typeParameterClass = typeParameterClass;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public EmployeeService() {

	}

	public EmployeeService(SessionFactory sessionFactory, Class<T> typeParameterClass) {
		this.sessionFactory = sessionFactory;
		this.typeParameterClass = typeParameterClass;
	}

	@Override
	public synchronized List<T> getEmployees(String orderTag) throws IllegalArgumentException {

		try (Session session = this.sessionFactory.openSession()) {

			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

			CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(this.typeParameterClass);

			Root<T> criteriaRoot = criteriaQuery.from(this.typeParameterClass);

			Path<String> employeeId = criteriaRoot.get(orderTag);

			criteriaQuery
					.select(criteriaRoot)
					.orderBy(criteriaBuilder.asc(employeeId))
			;

			Query<T> query = session.createQuery(criteriaQuery);

			List<T> tmp = query.list();

			logger.info("get");

			return tmp;
		}
	}

	@Override
	public synchronized T getEmployeeById(Long id) throws ObjectNotFoundException {

		if (id == null) throw new NotFoundException("non id");

		try (Session session = this.sessionFactory.openSession()) {

			logger.info("get");

			return session.get(typeParameterClass, id);
		}
	}

	@Override
	public synchronized T saveEmployeeById(T employee) {

		try (Session session = this.sessionFactory.openSession()) {

			try {

				session.beginTransaction();

				long id = (long) session.save(Objects.requireNonNull(employee));

				session.getTransaction().commit();

				return session.get(typeParameterClass, id);

			} catch (Exception e) {

				logger.error(e);

				if (session.getTransaction().isActive()) {
					session.getTransaction().rollback();
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
	@Override
	public synchronized T putEmployeeById(@NotNull T employee) {

		try (Session session = this.sessionFactory.openSession()) {

			try {

				T tmp = null;

				if (Objects.nonNull(employee.getEmployeeId()) && Objects.isNull(tmp = session.get(typeParameterClass, employee.getEmployeeId()))) {
					throw new NotFoundException("id is not found");
				}

				if (Objects.nonNull(tmp)) session.detach(tmp);

				session.beginTransaction();

				session.saveOrUpdate(Objects.requireNonNull(employee));

				session.getTransaction().commit();

				if (employee.getEmployeeId() == null) throw new NotFoundException("non id");

				return employee;

			} catch (Exception e) {

				logger.error(e);

				if (session.getTransaction().isActive()) {
					session.getTransaction().rollback();
				}

				throw e;
			}
		}
	}

	@Override
	public synchronized T patchEmployeeById(T employee) throws NotFoundException {

		try (Session session = this.sessionFactory.openSession()) {

			try {
				session.beginTransaction();

				T tmp = null;

				if (Objects.isNull(employee.getEmployeeId()) || Objects.isNull(tmp = session.get(typeParameterClass, employee.getEmployeeId()))) {
					throw new NotFoundException("ID not found during patch");
				}

				tmp.employeeUpdater(employee);

				session.update(Objects.requireNonNull(tmp));

				session.getTransaction().commit();

				return tmp;

			} catch (Exception e) {

				logger.error(e);

				if (session.getTransaction().isActive()) {
					session.getTransaction().rollback();
				}

				throw e;
			}
		}
	}

	@Override
	public synchronized T deleteEmployeeById(T employee) throws NotFoundException {

		if (employee.getEmployeeId() == null) throw new NotFoundException("non id");

		try (Session session = this.sessionFactory.openSession()) {

			try {

				session.beginTransaction();

				T tmp = session.get(typeParameterClass, employee.getEmployeeId());

				if (Objects.nonNull(tmp)) {

					session.detach(tmp);

					session.delete(employee);

					session.getTransaction().commit();

					return tmp;
				} else {
					throw new NotFoundException("entity was not found");
				}

			} catch (Exception e) {

				logger.error(e);

				if (session.getTransaction().isActive()) {
					session.getTransaction().rollback();
					logger.info("rollback");
				}

				throw e;
			}
		}
	}

}
