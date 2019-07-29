package com.example.mainapp.DAO;

import com.example.mainapp.DAO.entity.Employee;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;

import java.util.List;

public interface IHibernateUtil {
	void buidIn(String hibernateCFG, Class typeClass) throws RuntimeException;

	List<Employee> setUp(String hibernateCFG, Class typeClass, IHibernateUtilSessionFactory iHibernateUtilSessionFactory) throws RuntimeException;

	StandardServiceRegistry getServiceRegistryBuilder();

	SessionFactory getSessionFactory();
}
