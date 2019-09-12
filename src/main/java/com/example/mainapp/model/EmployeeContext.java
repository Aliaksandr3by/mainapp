package com.example.mainapp.model;

import com.example.mainapp.exeptions.NotFoundException;
import com.example.mainapp.model.entity.Department;
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
@Repository("employeeContext")
@RequestScope
public class EmployeeContext implements IContext<Employee> {

	private SessionFactory sessionFactory;

	private Class<Employee> clazzEmployee = Employee.class;
	private Class<Department> clazzDepartment = Department.class;

	private Logger logger;

	public EmployeeContext() {
	}

	public EmployeeContext(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Autowired
	public EmployeeContext(
			@Qualifier("sessionFactory") SessionFactory sessionFactory,
			@Qualifier("LOG") Logger logger) {
		this.sessionFactory = sessionFactory;
		this.logger = logger;
	}

	@Override
	public List<Employee> getAll(String sortOrder) throws IllegalStateException, IllegalArgumentException, HibernateException {
		try (Session session = this.sessionFactory.openSession()) {

			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

			CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(this.clazzEmployee);

			Root<Employee> criteriaRoot = criteriaQuery.from(this.clazzEmployee);

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
	public Employee load(Employee item) throws IllegalStateException, IllegalArgumentException, HibernateException {
		try {
			try (Session session = this.sessionFactory.openSession()) {

				Employee tmp = Hibernate.unproxy(session.load(clazzEmployee, item.getEmployeeId()), clazzEmployee);

				logger.info(tmp.toString());

				return tmp;
			}
		} catch (IllegalArgumentException | ObjectNotFoundException e) {

			logger.error(e.getMessage(), e);

			throw e;
		}
	}

	@Override
	public Employee create(Employee item) throws NotFoundException {
		try (Session session = this.sessionFactory.openSession()) {
			try {
				session.beginTransaction();

				if (Objects.nonNull(item) && Objects.nonNull(item.getEmployeeId())) {
					throw new NotFoundException("The object must not contain an ID");
				}

				if (Objects.nonNull(item.getDepartment()) && Objects.isNull(item.getDepartment().getIdDepartment())) {
					throw new NotFoundException("The object must contain a Department");
				}

				//FIXME не знаю что эта штука должна делать
				Department tmpD = null;
				if (Objects.nonNull(tmpD = session.load(Department.class, item.getDepartment().getIdDepartment()))) {
					tmpD = Hibernate.unproxy(tmpD, clazzDepartment);
					item.setDepartment(tmpD);
				}

				long id = (long) session.save(item);

				session.getTransaction().commit();

				Employee tmp = session.get(clazzEmployee, id);

				logger.info(tmp.toString());

				return tmp;

			} catch (NotFoundException e) {
				logger.error(e.getMessage(), e);
				if (session.getTransaction().isActive()) {
					session.getTransaction().rollback();
				}
				throw e;
			} catch (HibernateException e) {
				logger.error(e.getMessage(), e);
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
	public Employee update(@NotNull Employee item) {
		try (Session session = this.sessionFactory.openSession()) {

			try {

				Employee tmp = null;

				if (Objects.nonNull(item.getEmployeeId()) && Objects.isNull(tmp = session.get(clazzEmployee, item.getEmployeeId()))) {
					throw new NotFoundException("id is not found");
				}

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
	public Employee patch(Employee item) throws NotFoundException {
		try (Session session = this.sessionFactory.openSession()) {

			try {
				session.beginTransaction();

				Employee employee = null;
				Department department = null;
				if (Objects.isNull(item.getEmployeeId()) || Objects.isNull(employee = session.get(clazzEmployee, item.getEmployeeId()))) {
					throw new NotFoundException("ID employee not found during patch");
				}

				if (Objects.nonNull(item.getDepartment())
						&& Objects.nonNull(item.getDepartment().getIdDepartment())
						&& Objects.isNull(department = session.get(Department.class, item.getDepartment().getIdDepartment()))
				) {
					throw new NotFoundException("ID Department not found during patch");
				}

				//FIXME не знаю что эта штука должна делать с вложенными элементами
				employee.patcherEmployee(item);
				employee.setDepartment(department);

				session.update(employee);

				session.getTransaction().commit();

				return employee;

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
	public Employee delete(Employee item) throws NotFoundException {

		if (item.getEmployeeId() == null) throw new NotFoundException("non id");

		try (Session session = this.sessionFactory.openSession()) {

			try {

				session.beginTransaction();

				Employee tmp = session.get(clazzEmployee, item.getEmployeeId());

				if (Objects.nonNull(tmp)) {

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
