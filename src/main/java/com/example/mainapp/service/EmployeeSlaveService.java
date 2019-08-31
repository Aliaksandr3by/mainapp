package com.example.mainapp.service;

import com.example.mainapp.model.entity.Employee;
import com.example.mainapp.model.entity.EmployeeSlave;
import com.example.mainapp.model.entity.EmployeeSlavePK;
import com.example.mainapp.model.entity.Slave;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
@Qualifier(value = "providerEmployeeSlaveService")
public class EmployeeSlaveService {

	private SessionFactory sessionFactory;

	@Resource(name = "logger")
	private Logger logger;

	public EmployeeSlaveService() {
	}

	public EmployeeSlaveService(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	//test
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
