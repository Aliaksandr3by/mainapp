package com.example.mainapp.configuration;

import com.example.mainapp.model.HibernateUtil;
import com.example.mainapp.model.IHibernateUtil;
import com.example.mainapp.model.entity.Employee;
import com.example.mainapp.service.EmployeeContext;
import com.example.mainapp.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;


@Configuration
public class InjectConfiguration {

	@Bean
	public IHibernateUtil providerHibernateUtil() {

		return new HibernateUtil("hibernate.employeedb.cfg.xml");
	}

	@Bean
	public Logger logger() {

		return LogManager.getLogger(EmployeeService.class);
	}

	@Bean
	@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public EmployeeContext<Employee> providerEmployeeService() {

		return new EmployeeService<>(providerHibernateUtil().getSessionFactory(), Employee.class);
	}


}
