package com.example.mainapp.DAO;

import com.example.mainapp.DAO.entity.Employee;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;

import java.util.List;

public interface IHibernateUtil {
	SessionFactory buildIn(String hibernateCFG) throws RuntimeException;

	StandardServiceRegistry getServiceRegistryBuilder();

	SessionFactory getSessionFactory();

	void destroyStandardServiceRegistry();
}
