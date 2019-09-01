package com.example.mainapp.configuration;

import com.example.mainapp.helper.HibernateUtil;
import com.example.mainapp.model.EmployeeContext;
import com.example.mainapp.model.entity.Employee;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.ApplicationScope;

@Configuration
public class InjectConfiguration {

	@Bean(name = "LOG")
	public Logger logger() {
		Logger logger = LoggerFactory.getLogger(EmployeeContext.class);
		return logger;
	}

	@Deprecated
	@Bean("employeeClass")
	public Class<Employee> employeeClass() {
		return Employee.class;
	}

	@Bean("sessionFactory")
	@ApplicationScope
	public SessionFactory sessionFactory() {

		return new HibernateUtil("hibernate.employeedb.cfg.xml").getSessionFactory();
	}

}
