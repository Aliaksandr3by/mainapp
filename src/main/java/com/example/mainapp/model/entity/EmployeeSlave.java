package com.example.mainapp.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Access(AccessType.PROPERTY)
@Table(name = "slave_employee")
public class EmployeeSlave implements Serializable {


	private Slave slave;

	private Employee employee;

	public EmployeeSlave() {
	}

	public EmployeeSlave(Slave idSlave, Employee employeeId) {
		this.slave = idSlave;
		this.employee = employeeId;
	}

	@Id
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	public Slave getSlave() {
		return slave;
	}

	@Id
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	public Employee getEmployee() {
		return employee;
	}

	public void setSlave(Slave idSlave) {
		this.slave = idSlave;
	}

	public void setEmployee(Employee employeeId) {
		this.employee = employeeId;
	}

}