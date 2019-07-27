package com.example.mainapp.DAO;

import com.example.mainapp.DAO.entity.Employee;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;

import java.util.List;

public interface IHibernateUtil {
	void buidIn(String hibernateCFG, Class typeClass) throws Exception;

	List<Employee> setUp(Class typeClass, IHibernateUtilSession iHibernateUtilSession) throws Exception;

	StandardServiceRegistry getServiceRegistryBuilder();

	SessionFactory getSessionFactory();
}
