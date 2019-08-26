package com.example.mainapp.model;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;

public interface IHibernateUtil {

	SessionFactory buildIn(String hibernateCFG) throws RuntimeException;

	StandardServiceRegistry getServiceRegistryBuilder();

	SessionFactory getSessionFactory();

	void destroyStandardServiceRegistry();
}
