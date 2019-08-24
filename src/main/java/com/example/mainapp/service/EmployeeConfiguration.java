package com.example.mainapp.service;

import com.example.mainapp.DAO.HibernateUtil;
import com.example.mainapp.DAO.IHibernateUtil;
import com.example.mainapp.DAO.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class EmployeeConfiguration {

	@Bean
	@Scope("singleton")
	public IHibernateUtil providerHibernateUtil() {
		return new HibernateUtil("hibernate.employeedb.cfg.xml");
	}

	@Bean
	public Logger logger(){
		return LoggerFactory.getLogger(EmployeeService.class);
	}

	@Bean(name = "providerEmployeeService")
//	@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
//	@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
	@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public IEmployeeService<Employee> providerEmployeeService() {
		return new EmployeeService<>(providerHibernateUtil().getSessionFactory(), Employee.class, logger());
	}

}
