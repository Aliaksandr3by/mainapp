package com.example.mainapp.model.entity;

import java.io.Serializable;
import java.util.Objects;

public class EmployeeSlavePK  implements Serializable {

	private Long idSlave;

	private Long employeeId;

	public EmployeeSlavePK() {
	}

	public EmployeeSlavePK(Long subsystem, Long username) {
		this.idSlave = subsystem;
		this.employeeId = username;
	}

	public Long getIdSlave() {
		return idSlave;
	}

	public void setIdSlave(Long idSlave) {
		this.idSlave = idSlave;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		EmployeeSlavePK pk = (EmployeeSlavePK) o;
		return Objects.equals(idSlave, pk.idSlave) &&
				Objects.equals(employeeId, pk.employeeId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idSlave, employeeId);
	}
}
