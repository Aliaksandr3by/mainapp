package com.example.mainapp.model;

import com.example.mainapp.exeptions.NotFoundException;
import com.example.mainapp.model.entity.Employee;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

//предназначен для хранения, извлечения и поиска. Как правило, используется для работы с базами данных.
@Repository("employeeComponent")
@RequestScope
public class EmployeeContext implements IEmployeeContext<Employee> {

	public static int count = 0;

	private SessionFactory sessionFactory;

	private Class<Employee> typeClass;

	private Logger logger;

	public EmployeeContext() {
	}

	public EmployeeContext(SessionFactory sessionFactory) {
		++count;
		this.sessionFactory = sessionFactory;
	}

	//FIXME
	@Autowired
	public EmployeeContext(
			@Qualifier("sessionFactory") SessionFactory sessionFactory,
			@Qualifier("employeeClass") Class<Employee> typeClass,
			@Qualifier("LOG") Logger logger) {
		this.sessionFactory = sessionFactory;
		this.typeClass = typeClass;
		this.logger = logger;
	}

	@Override
	public List<Employee> getEmployees(String sortOrder) throws Exception {
		try (Session session = this.sessionFactory.openSession()) {

			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

			CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(this.typeClass);

			Root<Employee> criteriaRoot = criteriaQuery.from(this.typeClass);

			Path<String> employeeId = criteriaRoot.get(sortOrder);

			criteriaQuery
					.select(criteriaRoot)
					.orderBy(criteriaBuilder.asc(employeeId))
			;

			Query<Employee> query = session.createQuery(criteriaQuery);

			List<Employee> tmp = query.list();

			logger.info(tmp.toString());

			return tmp;
		}
	}

	@Override
	public Employee loadEmployeeById(Employee item) throws ObjectNotFoundException {
		try {
			try (Session session = this.sessionFactory.openSession()) {

				Employee element = session.load(typeClass, item.getEmployeeId());

				Employee tmp = Hibernate.unproxy(element, typeClass);

				logger.info(tmp.toString());

				return tmp;
			}
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw e;
		}
	}

	@Override
	public Employee createEmployee(Employee item) throws Exception {
		try (Session session = this.sessionFactory.openSession()) {

			try {

				session.beginTransaction();

				if (Objects.nonNull(item.getEmployeeId())) {
					throw new Exception("The object must not contain an ID");
				}
				if (item.IsEmpty()) {
					throw new Exception("The object has a null properties");
				}

				long id = (long) session.save(item);

				session.getTransaction().commit();

				Employee tmp = session.get(typeClass, id);

				logger.info(tmp.toString());

				return tmp;

			} catch (Exception e) {

				logger.error(e.getMessage(), e);

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
	public Employee updateEmployee(@NotNull Employee item) {
		try (Session session = this.sessionFactory.openSession()) {

			try {

				Employee tmp = null;

				if (Objects.nonNull(item.getEmployeeId()) && Objects.isNull(tmp = session.get(typeClass, item.getEmployeeId()))) {
					throw new NotFoundException("id is not found");
				}

				//if (Objects.nonNull(tmp)) session.detach(tmp);

				session.beginTransaction();

				session.saveOrUpdate(tmp);

				session.getTransaction().commit();

				if (item.getEmployeeId() == null) throw new NotFoundException("non id");

				return item;

			} catch (Exception e) {

				logger.error(e.getMessage(), e);

				if (session.getTransaction().isActive()) {
					session.getTransaction().rollback();
				}

				throw e;
			}
		}
	}

	@Override
	public Employee patchEmployee(Employee item) throws NotFoundException {
		try (Session session = this.sessionFactory.openSession()) {

			try {
				session.beginTransaction();

				Employee tmp = null;

				if (Objects.isNull(item.getEmployeeId()) || Objects.isNull(tmp = session.get(typeClass, item.getEmployeeId()))) {
					throw new NotFoundException("ID not found during patch");
				}

				tmp.employeeUpdater(item);

				session.update(tmp);

				session.getTransaction().commit();

				return tmp;

			} catch (Exception e) {

				logger.error(e.getMessage(), e);

				if (session.getTransaction().isActive()) {
					session.getTransaction().rollback();
				}

				throw e;
			}
		}
	}

	@Override
	public Employee deleteEmployee(Employee item) throws NotFoundException {

		if (item.getEmployeeId() == null) throw new NotFoundException("non id");

		try (Session session = this.sessionFactory.openSession()) {

			try {

				session.beginTransaction();

				Employee tmp = session.get(typeClass, item.getEmployeeId());

				if (Objects.nonNull(tmp)) {

					//session.detach(tmp);

					session.delete(tmp);

					session.getTransaction().commit();

					return tmp;
				} else {
					throw new NotFoundException("entity was not found");
				}

			} catch (Exception e) {

				logger.error(e.getMessage(), e);

				if (session.getTransaction().isActive()) {
					session.getTransaction().rollback();
					logger.info("rollback");
				}

				throw e;
			}
		}
	}

}
