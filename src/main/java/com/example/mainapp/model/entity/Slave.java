package com.example.mainapp.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "slave", schema = "public")
@Access(AccessType.PROPERTY)
public class Slave implements Serializable {

	private Long idSlave;
	private String nameSlave;
	private Collection<EmployeeSlave> employees = new ArrayList<>();

	public Slave() {
	}

	public Slave(Long idSlave, String nameSlave) {
		this.idSlave = idSlave;
		this.nameSlave = nameSlave;
	}

	@JsonIgnore
	@OneToMany(
			fetch = FetchType.EAGER,
			mappedBy = "slave",
			cascade = {CascadeType.PERSIST, CascadeType.MERGE},
			orphanRemoval = true
	)
	public Collection<EmployeeSlave> getEmployees() {
		return employees;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "slave_id_seq")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "id_slave", nullable = false)
	public Long getIdSlave() {
		return idSlave;
	}

	@Column(name = "name_slave", nullable = false)
	@Type(type = "text")
	public String getNameSlave() {
		return nameSlave;
	}

	public void setEmployees(Collection<EmployeeSlave> employees) {
		this.employees = employees;
	}

	public void setIdSlave(Long idSlave) {
		this.idSlave = idSlave;
	}

	public void setNameSlave(String nameSlave) {
		this.nameSlave = nameSlave;
	}


}