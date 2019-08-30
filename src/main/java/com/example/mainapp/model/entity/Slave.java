package com.example.mainapp.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "slave", schema = "public")
@Access(AccessType.PROPERTY)
public class Slave implements Serializable {

	private Long idSlave;
	private String nameSlave;
	private Collection<Employee> employees;

	public Slave() {
	}

	public Slave(Long idSlave, String nameSlave) {
		this.idSlave = idSlave;
		this.nameSlave = nameSlave;
	}

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "slave_employee",
			joinColumns = @JoinColumn(name = "id_slave", referencedColumnName = "id_slave"),
			inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
	)
	public Collection<Employee> getEmployees() {
		return employees;
	}


	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "department_id_seq")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Id
	@Column(name = "id_slave", nullable = false)
	public Long getIdSlave() {
		return idSlave;
	}

	@Column(name = "name_slave", nullable = false)
	@Type(type = "text")
	public String getNameSlave() {
		return nameSlave;
	}

	public void setEmployees(Collection<Employee> employees) {
		this.employees = employees;
	}

	public void setIdSlave(Long idSlave) {
		this.idSlave = idSlave;
	}

	public void setNameSlave(String nameSlave) {
		this.nameSlave = nameSlave;
	}


}