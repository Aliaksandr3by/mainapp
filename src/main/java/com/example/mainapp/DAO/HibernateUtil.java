package com.example.mainapp.DAO;

import com.example.mainapp.DAO.entity.Employee;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Qualifier(value = "hibernateUtil")
public class HibernateUtil implements IHibernateUtil {

	public StandardServiceRegistry getServiceRegistryBuilder() {
		return serviceRegistryBuilder;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	private StandardServiceRegistry serviceRegistryBuilder;

	private SessionFactory sessionFactory;


	public HibernateUtil() {
	}

	public void buidIn(String hibernateCFG, Class typeClass) throws RuntimeException {

		try {

			this.serviceRegistryBuilder = new StandardServiceRegistryBuilder()
					.configure(Objects.requireNonNull(hibernateCFG, "hibernate.cfg.xml"))
					.build();

			this.sessionFactory = new MetadataSources(this.serviceRegistryBuilder)
					.buildMetadata()
					.buildSessionFactory();

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

	public List<Employee> setUp(String hibernateCFG, Class typeClass, IHibernateUtilSession iHibernateUtilSession) throws RuntimeException {

		try {

			try (StandardServiceRegistry serviceRegistryBuilder = new StandardServiceRegistryBuilder()
					.configure(Objects.requireNonNull(hibernateCFG, "hibernate.cfg.xml"))
					.build()) {

				try (SessionFactory sessionFactory = new MetadataSources(serviceRegistryBuilder)
						.buildMetadata()
						.buildSessionFactory()) {

					return iHibernateUtilSession.Func(sessionFactory);
				}
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}

	}

}

