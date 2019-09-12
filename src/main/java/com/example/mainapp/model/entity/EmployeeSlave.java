package com.example.mainapp.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

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

	public void setSlave(Slave slave) {
		this.slave = slave;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof EmployeeSlave)) return false;
		EmployeeSlave that = (EmployeeSlave) o;
		return slave.equals(that.slave) &&
				employee.equals(that.employee);
	}

	@Override
	public int hashCode() {
		return Objects.hash(slave, employee);
	}

	@Override
	public String toString() {
		return "EmployeeSlave{" +
				"slave=" + slave +
				", employee=" + employee +
				'}';
	}
}