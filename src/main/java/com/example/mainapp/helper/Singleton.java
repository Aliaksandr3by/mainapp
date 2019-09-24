package com.example.mainapp.helper;

import com.example.mainapp.entity.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class Singleton {

	private static transient volatile Singleton instance; //важен порядок инициализации статик. переменных

	private final List<Employee> model = new ArrayList<>();

	private Singleton() throws IllegalStateException {
		if (instance != null) {
			throw new IllegalStateException("Singleton already initialized");
		}
	}

	public static Singleton getInstance() {

		if (instance == null) {
			synchronized (Singleton.class) {
				if (instance == null) {
					instance = new Singleton();
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

	public static void main(String ...args){

	}

}