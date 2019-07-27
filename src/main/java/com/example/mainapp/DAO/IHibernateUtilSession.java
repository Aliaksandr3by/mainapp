package com.example.mainapp.DAO;

import com.example.mainapp.DAO.entity.Employee;
import org.hibernate.SessionFactory;

import java.util.List;

public interface IHibernateUtilSession {
	List<Employee> Func(SessionFactory sessionFactory);
}
