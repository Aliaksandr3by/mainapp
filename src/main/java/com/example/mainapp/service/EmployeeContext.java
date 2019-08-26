package com.example.mainapp.service;

import com.example.mainapp.model.entity.Employee;
import com.example.mainapp.exeptions.NotFoundException;
import org.hibernate.ObjectNotFoundException;

import java.util.Collection;

public interface EmployeeContext<T extends Employee> {

	Collection<T> getEmployees(String sortOrder);

	T loadEmployeeById(T item) throws ObjectNotFoundException;

	T createEmployee(T item) throws IllegalStateException, IllegalArgumentException;

	T updateEmployee(T item);

	T patchEmployee(T item) throws NotFoundException;

	T deleteEmployee(T item) throws NotFoundException;
}
