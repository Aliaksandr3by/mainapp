package com.example.mainapp.configuration;

import com.example.mainapp.MainappApplication;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Configuration
//указывает где Spring искать классы, помеченные аннотацией @Component
//@ComponentScan(basePackageClasses = {com.example.mainapp.MainappApplication.class})
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AppConfiguration {

	private Logger logger = LoggerFactory.getLogger(MainappApplication.class);

	@PostConstruct
	public void init() {
		logger.warn("init");
	}

	@PreDestroy
	public void destroy() {
		logger.warn("destroy");
	}

	@Bean(name = "LOG") // используется в конфигурационных классах для непосредственного создания бина.
	@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public Logger logger() {

		return logger;
	}

	@Bean("sessionFactory")
	@SessionScope
	public SessionFactory sessionFactory() {

		return new HibernateConfiguration(this.logger).build("hibernate.employeedb.cfg.xml");
	}

	@Bean("entityManagerFactory")
	@SessionScope
	public EntityManagerFactory managerFactory() {
		return Persistence
				.createEntityManagerFactory("CRM")
				;
	}

}
