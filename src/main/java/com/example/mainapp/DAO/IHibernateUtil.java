package com.example.mainapp.DAO;

import com.example.mainapp.DAO.entity.Employee;
import org.hibernate.boot.registry.StandardServiceRegistry;

import java.util.List;

public interface IHibernateUtil {
	StandardServiceRegistry buidIn(String hibernateCFG) throws Exception;

	List<Employee> setUp(Class typeClass, IHibernateUtilSession iHibernateUtilSession) throws Exception;

	StandardServiceRegistry getServiceRegistryBuilder();
}
