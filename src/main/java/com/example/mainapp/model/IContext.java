package com.example.mainapp.model;

import com.example.mainapp.exeptions.NotFoundException;
import com.example.mainapp.model.entity.Employee;
import org.hibernate.ObjectNotFoundException;

import java.util.Collection;

public interface IContext<T> {

	Collection<T> getAll(String sortOrder) throws Exception;

	T load(T item) throws Exception;

	T create(T item) throws Exception;

	T update(T item) throws Exception;

	T delete(T item) throws Exception;

	T patch(T item) throws Exception;

}
