package com.example.mainapp.service;

import com.example.mainapp.DAO.entity.Employee;
import com.example.mainapp.exeptions.NotFoundException;
import org.hibernate.ObjectNotFoundException;

import java.util.List;

public interface IEmployeeService<T extends Employee> {

	List<T> getEmployees(String orderBy);

	T getEmployeeById(Long id) throws ObjectNotFoundException;

	T saveEmployeeById(T employee);

	boolean putEmployeeById(T employee);

	boolean patchEmployeeById(T employee) throws NotFoundException;

	boolean deleteEmployeeById(T employee) throws NotFoundException;
}
