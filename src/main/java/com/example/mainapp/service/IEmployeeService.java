package com.example.mainapp.service;

import com.example.mainapp.exeptions.NotFoundException;
import com.example.mainapp.model.entity.Employee;
import com.example.mainapp.model.entity.EmployeeSlave;
import org.hibernate.ObjectNotFoundException;

import java.util.Collection;

public interface IEmployeeService<T extends Employee> {

	Collection<T> getEmployees(String sortOrder);

	T loadEmployeeById(T item) throws ObjectNotFoundException;

	T createEmployee(T item) throws Exception;

	T updateEmployee(T item);

	T patchEmployee(T item) throws NotFoundException;

	T deleteEmployee(T item) throws NotFoundException;

}
