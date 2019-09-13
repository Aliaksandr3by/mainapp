package com.example.mainapp.model;

import com.example.mainapp.exeptions.NotFoundException;
import com.example.mainapp.exeptions.NotImplementedException;
import com.example.mainapp.model.entity.Employee;
import com.example.mainapp.model.entity.EmployeeSlave;
import com.example.mainapp.model.entity.Slave;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.springframework.web.context.annotation.RequestScope;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Named("employeeSlaveContext")
@RequestScope
public class EmployeeSlaveContext implements IContext<EmployeeSlave> {

	public static int count = 0;

	private Class<EmployeeSlave> typeClass = EmployeeSlave.class;

	private SessionFactory sessionFactory;

	//@Resource(name = "LOG")
	private Logger logger;

	public EmployeeSlaveContext() {
	}

	@Inject
	public EmployeeSlaveContext(@Named("sessionFactory") SessionFactory sessionFactory, @Named("LOG") Logger logger) {
		++count;
		this.sessionFactory = sessionFactory;
		this.logger = logger;
	}

	@Override
	public List<EmployeeSlave> findAll(String sortOrder) {
		try (Session session = this.sessionFactory.openSession()) {

			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

			CriteriaQuery<EmployeeSlave> criteriaQuery = criteriaBuilder.createQuery(this.typeClass);

			Root<EmployeeSlave> criteriaRoot = criteriaQuery.from(this.typeClass);

			criteriaQuery
					.select(criteriaRoot)
			;

			Query<EmployeeSlave> query = session.createQuery(criteriaQuery);

			List<EmployeeSlave> tmp = query.list();

			logger.info(tmp.toString());

			return tmp;
		}
	}

	@Override
	public EmployeeSlave insert(EmployeeSlave employeeSlavePK) {
		try (Session session = this.sessionFactory.openSession()) {

			try {

				session.beginTransaction();

				Employee employee = session.get(Employee.class, employeeSlavePK.getEmployee().getEmployeeId());
				Slave slave = session.get(Slave.class, employeeSlavePK.getSlave().getIdSlave());

				EmployeeSlave employeeSlave = new EmployeeSlave(slave, employee);

				EmployeeSlave id = (EmployeeSlave) session.save(employeeSlave);

				session.getTransaction().commit();

				EmployeeSlave tmp = session.get(EmployeeSlave.class, id);

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

	@Override
	public EmployeeSlave findById(EmployeeSlave item) throws ObjectNotFoundException {
		throw new NotImplementedException("Method is not implemented");
	}

	@Override
	public EmployeeSlave update(EmployeeSlave item) {
		throw new NotImplementedException("Method is not implemented");
	}

	@Override
	public EmployeeSlave delete(EmployeeSlave item) throws NotFoundException {
		throw new NotImplementedException("Method is not implemented");
	}

	@Override
	public EmployeeSlave patch(EmployeeSlave item) throws NotFoundException {
		throw new NotImplementedException("Method is not implemented");
	}
}
