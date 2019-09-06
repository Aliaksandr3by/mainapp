package com.example.mainapp.configuration;

import com.example.mainapp.MainappApplication;
import com.example.mainapp.helper.HibernateUtil;
import com.example.mainapp.model.entity.Employee;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.SessionScope;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

@Configuration
//@ComponentScan("com.example.mainapp") //указывает где Spring искать классы, помеченные аннотацией @Component
public class InjectConfiguration {

	@Bean(name = "LOG") // используется в конфигурационных классах для непосредственного создания бина.
	@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public Logger logger() {
		Logger logger = LoggerFactory.getLogger(MainappApplication.class);
		return logger;
	}

	@Deprecated
	@Bean("employeeClass")
	public Class<Employee> employeeClass() {
		return Employee.class;
	}

	@Bean("sessionFactory")
	@SessionScope
	public SessionFactory sessionFactory() {

		return new HibernateUtil("hibernate.employeedb.cfg.xml").getSessionFactory();
	}

	@Bean("entityManagerFactory")
	@SessionScope
	public EntityManagerFactory managerFactory() {
		return Persistence
				.createEntityManagerFactory("CRM")
				;
	}

}
