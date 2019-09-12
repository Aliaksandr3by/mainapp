package com.example.mainapp.configuration;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component //определяет этот класс как кандидата для создания bean.
@Qualifier(value = "hibernateUtil")
public class HibernateConfiguration {

	private Logger logger;

	public HibernateConfiguration() {
	}

	public HibernateConfiguration(Logger logger) {
		this.logger = logger;
	}

	public SessionFactory build(String hibernateCFG) {
		try {
			return new MetadataSources(new StandardServiceRegistryBuilder().configure(Optional.ofNullable(hibernateCFG).orElse("hibernate.cfg.xml"))
					.build())
					.buildMetadata()
					.buildSessionFactory();
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
}
