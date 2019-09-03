package com.example.mainapp.model;

import com.example.mainapp.model.entity.Employee;
import com.example.mainapp.model.entity.EmployeeSlave;
import com.example.mainapp.model.entity.EmployeeSlavePK;
import com.example.mainapp.model.entity.Slave;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.springframework.web.context.annotation.RequestScope;

import javax.inject.Inject;
import javax.inject.Named;

@Named("employeeSlaveContext")
@RequestScope
//@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EmployeeSlaveContext {

	public static int count = 0;

	private SessionFactory sessionFactory;

	private Logger logger;

	public EmployeeSlaveContext() {
	}

	@Inject
	public EmployeeSlaveContext(@Named("sessionFactory") SessionFactory sessionFactory, @Named("LOG") Logger logger) {
		++count;
		this.sessionFactory = sessionFactory;
		this.logger = logger;
	}

	public EmployeeSlave createEmployeeSlave(EmployeeSlavePK employeeSlavePK) {
		try (Session session = this.sessionFactory.openSession()) {

			try {

				session.beginTransaction();

				Employee employee = session.get(Employee.class, employeeSlavePK.getEmployeeId());
				Slave slave = session.get(Slave.class, employeeSlavePK.getIdSlave());

				EmployeeSlave employeeSlave = new EmployeeSlave(slave.getIdSlave(), employee.getEmployeeId());

				EmployeeSlavePK id = (EmployeeSlavePK) session.save(employeeSlave);

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
}
