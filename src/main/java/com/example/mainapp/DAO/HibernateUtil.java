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

	private StandardServiceRegistry serviceRegistryBuilder;

	public StandardServiceRegistry getServiceRegistryBuilder() {
		return serviceRegistryBuilder;
	}

	public HibernateUtil() {
	}

	public StandardServiceRegistry buidIn(String hibernateCFG) throws Exception {

		try {

			serviceRegistryBuilder = new StandardServiceRegistryBuilder()
					.configure(Objects.requireNonNull(hibernateCFG, "hibernate.cfg.xml"))
					.build();

			return serviceRegistryBuilder;

		} catch (Exception e) {
			e.printStackTrace();
			StandardServiceRegistryBuilder.destroy(this.serviceRegistryBuilder);
			throw e;
		}

	}

	public List<Employee> setUp(Class typeClass, IHibernateUtilSession iHibernateUtilSession) throws Exception {

		try {
			SessionFactory sessionFactory = new MetadataSources(this.serviceRegistryBuilder)
					.addAnnotatedClass(typeClass)
					.buildMetadata()
					.buildSessionFactory();

			return iHibernateUtilSession.Func(sessionFactory);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

}

