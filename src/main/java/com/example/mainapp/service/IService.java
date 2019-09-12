package com.example.mainapp.service;

import org.hibernate.ObjectNotFoundException;

import java.util.List;

public interface IService<T> {

	List<T> getAll(String sortOrder) throws Exception;

	T load(T item) throws ObjectNotFoundException;

	T create(T item);

	T delete(T item);

	T update(T item);

	T patch(T item);
}
