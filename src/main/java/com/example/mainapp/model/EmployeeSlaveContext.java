package com.example.mainapp.model;

import com.example.mainapp.model.entity.Employee;
import com.example.mainapp.model.entity.EmployeeSlave;
import com.example.mainapp.model.entity.EmployeeSlavePK;
import com.example.mainapp.model.entity.Slave;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import javax.inject.Named;


@Named("employeeSlaveContext")
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EmployeeSlaveContext {


	private SessionFactory sessionFactory;

	private Logger logger;

	public EmployeeSlaveContext() {
	}

	@Inject
	public EmployeeSlaveContext(@Named("sessionFactory") SessionFactory sessionFactory, @Named("LOG") Logger logger) {
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

				logger.error(e);

				if (session.getTransaction().isActive()) {
					session.getTransaction().rollback();
				}

				throw e;
			}
		}
	}
}
