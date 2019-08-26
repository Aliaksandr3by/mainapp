package com.example.mainapp.service;

import com.example.mainapp.DAO.entity.Employee;
import com.example.mainapp.exeptions.NotFoundException;
import org.apache.logging.log4j.Logger;
import org.hibernate.*;
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
@Qualifier(value = "providerEmployeeService")
public class EmployeeService<T extends Employee> implements EmployeeContext<T> {

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
	public synchronized List<T> getEmployees(String sortOrder) throws IllegalStateException, IllegalArgumentException {

		try (Session session = this.sessionFactory.openSession()) {

			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

			CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(this.typeParameterClass);

			Root<T> criteriaRoot = criteriaQuery.from(this.typeParameterClass);

			Path<String> employeeId = criteriaRoot.get(sortOrder);

			criteriaQuery
					.select(criteriaRoot)
					.orderBy(criteriaBuilder.asc(employeeId))
			;

			Query<T> query = session.createQuery(criteriaQuery);

			List<T> tmp = query.list();

			logger.info(tmp.toString());

			return tmp;
		}
	}

	@Override
	public synchronized T loadEmployeeById(T item) throws ObjectNotFoundException {

		try {
			try (Session session = this.sessionFactory.openSession()) {

				T element = session.load(typeParameterClass, item.getEmployeeId());

				T tmp = Hibernate.unproxy(element, typeParameterClass);

				logger.info(tmp.toString());

				return tmp;
			}
		} catch (Exception e) {

			logger.error(e);

			throw e;
		}
	}

	@Override
	public synchronized T createEmployee(T item) {

		try (Session session = this.sessionFactory.openSession()) {

			try {

				session.beginTransaction();

//				Department tmp = session.get(Department.class, employee.getDepartment().getIdDepartment());
//				employee.setDepartment(tmp);
				long id = (long) session.save(Objects.requireNonNull(item));

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
	 * @param item
	 * @return
	 * @throws HibernateException
	 */
	@Override
	public synchronized T updateEmployee(@NotNull T item) {

		try (Session session = this.sessionFactory.openSession()) {

			try {

				T tmp = null;

				if (Objects.nonNull(item.getEmployeeId()) && Objects.isNull(tmp = session.get(typeParameterClass, item.getEmployeeId()))) {
					throw new NotFoundException("id is not found");
				}

				if (Objects.nonNull(tmp)) session.detach(tmp);

				session.beginTransaction();

				session.saveOrUpdate(Objects.requireNonNull(item));

				session.getTransaction().commit();

				if (item.getEmployeeId() == null) throw new NotFoundException("non id");

				return item;

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
	public synchronized T patchEmployee(T item) throws NotFoundException {

		try (Session session = this.sessionFactory.openSession()) {

			try {
				session.beginTransaction();

				T tmp = null;

				if (Objects.isNull(item.getEmployeeId()) || Objects.isNull(tmp = session.get(typeParameterClass, item.getEmployeeId()))) {
					throw new NotFoundException("ID not found during patch");
				}

				tmp.employeeUpdater(item);

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
	public synchronized T deleteEmployee(T item) throws NotFoundException {

		if (item.getEmployeeId() == null) throw new NotFoundException("non id");

		try (Session session = this.sessionFactory.openSession()) {

			try {

				session.beginTransaction();

				T tmp = session.get(typeParameterClass, item.getEmployeeId());

				if (Objects.nonNull(tmp)) {

					session.detach(tmp);

					session.delete(item);

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
