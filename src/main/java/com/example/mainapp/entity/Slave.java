package com.example.mainapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "slave", schema = "public")
@Access(AccessType.PROPERTY)
public class Slave implements Serializable {

	private Long idSlave;
	private String nameSlave;
	private Collection<EmployeeSlave> employees = new ArrayList<>();

	public Slave() {
	}

	public Slave(Long idSlave) {
		this.idSlave = idSlave;
	}

	public Slave(Long idSlave, String nameSlave) {
		this.idSlave = idSlave;
		this.nameSlave = nameSlave;
	}

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "slave", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
	public Collection<EmployeeSlave> getEmployees() {
		return employees;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "slave_id_seq")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "id_slave", nullable = false, updatable = false)
	public Long getIdSlave() {
		return idSlave;
	}

	@Column(name = "name_slave", nullable = false)
	@Type(type = "text")
	public String getNameSlave() {
		return nameSlave;
	}

	public void setIdSlave(Long idSlave) {
		this.idSlave = idSlave;
	}

	public void setNameSlave(String nameSlave) {
		this.nameSlave = nameSlave;
	}

	public void setEmployees(Collection<EmployeeSlave> employees) {
		this.employees = employees;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Slave slave = (Slave) o;
		return idSlave.equals(slave.idSlave) &&
				nameSlave.equals(slave.nameSlave) &&
				employees.equals(slave.employees);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idSlave, nameSlave);
	}

	@Override
	public String toString() {
		return "Slave{" +
				"idSlave=" + idSlave +
				", nameSlave='" + nameSlave + '\'' +
				'}';
	}
}