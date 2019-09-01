package com.example.mainapp.configuration;

import com.example.mainapp.model.*;
import com.example.mainapp.model.entity.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;


@Configuration
public class InjectConfiguration {

	@Bean(name = "LOG")
	public Logger logger() {
		return LogManager.getLogger(EmployeeContext.class);
	}

	@Bean
	public SessionFactory sessionFactory() {

		return new HibernateUtil("hibernate.employeedb.cfg.xml").getSessionFactory();
	}

	//@Bean("typeClass")
	@EmployeeQualifier
	public  Class<Employee> typeClass() {

		return Employee.class;
	}

	@Bean("employeeContext")
	@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public IEmployeeContext<Employee> providerEmployeeConfiguration() {

		return new EmployeeContext<>(sessionFactory(), Employee.class);
	}

//	@Bean
//	@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
//	public EmployeeSlaveContext providerEmployeeSlaveConfiguration() {
//
//		return new EmployeeSlaveContext(providerSessionFactory());
//	}
}
