package com.example.mainapp.model;

import org.hibernate.ObjectNotFoundException;

import java.util.Collection;

public interface IContext<T> {

	Collection<T> findAll(String sortOrder) throws Exception;

	T findById(T item) throws ObjectNotFoundException;

	T insert(T item);

	T update(T item);

	T delete(T item);

	T patch(T item);
}
