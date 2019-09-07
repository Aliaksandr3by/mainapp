package com.example.mainapp.model.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Access(AccessType.FIELD)
@Table(name = "slave_employee")
@IdClass(value = EmployeeSlavePK.class)
public class EmployeeSlave implements Serializable {
	@Id
	@Column(name = "id_slave")
	private Long idSlave;

	@Id
	@Column(name = "employee_id")
	private Long employeeId;

	public EmployeeSlave() {
	}

	public EmployeeSlave(Long idSlave, Long employeeId) {
		this.idSlave = idSlave;
		this.employeeId = employeeId;
	}

	public Long getIdSlave() {
		return idSlave;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setIdSlave(Long idSlave) {
		this.idSlave = idSlave;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

}