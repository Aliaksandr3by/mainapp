package com.example.mainapp.model;

import com.example.mainapp.exeptions.NotFoundException;
import com.example.mainapp.model.entity.Employee;
import org.hibernate.ObjectNotFoundException;

import java.util.Collection;

public interface DataContext<T> {

	Collection<T> getAll(String sortOrder) throws Exception;

	T load(T item) throws ObjectNotFoundException;

	T create(T item) throws Exception;

	T update(T item);

	T delete(T item) throws NotFoundException;

	T patch(T item) throws NotFoundException;

}
