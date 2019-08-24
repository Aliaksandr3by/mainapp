package com.example.mainapp.DAO;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Qualifier(value = "hibernateUtil")
public class HibernateUtil implements IHibernateUtil {

	private StandardServiceRegistry serviceRegistryBuilder;

	private SessionFactory sessionFactory;

	private String hibernateCFG;

	public StandardServiceRegistry getServiceRegistryBuilder() {
		return serviceRegistryBuilder;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public String getHibernateCFG() {
		return hibernateCFG;
	}

	public HibernateUtil() {
	}

	public HibernateUtil(String hibernateCFG) {
		this.buildIn(hibernateCFG);
	}

	public SessionFactory buildIn(String hibernateCFG) throws RuntimeException {

		this.hibernateCFG = hibernateCFG;
		return this.build();
	}

	public SessionFactory build() throws RuntimeException {

		try {

			String tmp = Optional.ofNullable(this.hibernateCFG).orElse("hibernate.cfg.xml");

			this.serviceRegistryBuilder = new StandardServiceRegistryBuilder()
					.configure(tmp)
					.build();

			this.sessionFactory = new MetadataSources(this.serviceRegistryBuilder)
					.buildMetadata()
					.buildSessionFactory();

			return this.sessionFactory;

//			serviceRegistryBuilder.close();
//			sessionFactory.close();

		} catch (NullPointerException e) {
			e.printStackTrace();
			StandardServiceRegistryBuilder.destroy(this.serviceRegistryBuilder);
			throw e;
		} catch (RuntimeException e) {
			e.printStackTrace();
			StandardServiceRegistryBuilder.destroy(this.serviceRegistryBuilder);
			throw e;
		}

	}

	public void destroyStandardServiceRegistry() {
		StandardServiceRegistryBuilder.destroy(this.serviceRegistryBuilder);
	}

}
