package com.example.mainapp.DAO;

import com.example.mainapp.DAO.entity.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class Model {

	private static transient volatile Model instance; //важен порядок инициализации статик. переменных

	private final List<Employee> model = new ArrayList<>();

	private Model() throws IllegalStateException {
		if (instance != null) {
			throw new IllegalStateException("Singleton already initialized");
		}
	}

	public static Model getInstance() {

		if (instance == null) {
			synchronized (Model.class) {
				if (instance == null) {
					instance = new Model();
				}
			}
		}

		return instance;
	}

	protected Object readResolve() {
		return instance;
	}

	public void add(Employee user) {

		model.add(user);
	}

	public void addAll(List<Employee> user) {

		model.addAll(user);
	}

	public List<Employee> getModel() {

		return model;
	}

	public List<Employee> getList() {
		return model.stream().collect(Collectors.toList());
	}
}