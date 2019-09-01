package com.example.mainapp.configuration;

import com.example.mainapp.model.EmployeeContext;
import com.example.mainapp.model.HibernateUtil;
import com.example.mainapp.model.IEmployeeContext;
import com.example.mainapp.model.entity.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;


@Configuration
public class InjectConfiguration {

	@Bean(name = "LOG")
	public Logger logger() {

		return LogManager.getLogger(EmployeeContext.class);
	}

	@Bean
	public Class<Employee> employeeClass(){
		return Employee.class;
	}

	@Bean
	public SessionFactory sessionFactory() {

		return new HibernateUtil("hibernate.employeedb.cfg.xml").getSessionFactory();
	}

	@Bean("providerEmployeeContext")
	@RequestScope
	public IEmployeeContext<Employee> providerEmployeeConfiguration() {

		return new EmployeeContext<>(sessionFactory(), Employee.class);
	}

}
