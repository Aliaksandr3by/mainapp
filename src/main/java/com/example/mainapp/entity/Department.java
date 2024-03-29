package com.example.mainapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "department", schema = "public")
@Access(AccessType.PROPERTY)
public class Department implements Serializable {

	private Set<Employee> departmentSet;
	private Long idDepartment;
	private String nameDepartment;
	private LocalDateTime dateDepartment;

	public Department patcherDepartment(Department patch) {

		if (patch.getNameDepartment() != null) this.setNameDepartment(patch.getNameDepartment());
		if (patch.getDateDepartment() != null) this.setDateDepartment(patch.getDateDepartment());

		return this;
	}

	public Department() {
	}

	public Department(Long idDepartment) {
		this.idDepartment = idDepartment;
	}

	public Department(Long idDepartment, String nameDepartment, LocalDateTime dateDepartment) {
		this.idDepartment = idDepartment;
		this.nameDepartment = nameDepartment;
		this.dateDepartment = dateDepartment;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "department", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, orphanRemoval = true)
	public Set<Employee> getDepartmentSet() {
		return departmentSet;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "department_id_seq")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "id_department", nullable = false, updatable = false)
	public Long getIdDepartment() {
		return idDepartment;
	}

	@Column(name = "name_department", nullable = false)
	@Type(type = "text")
	public String getNameDepartment() {
		return nameDepartment;
	}

	@Column(name = "date_department", nullable = false)
	@Type(type = "LocalDateTime")
	public LocalDateTime getDateDepartment() {
		return dateDepartment;
	}

	public void setDepartmentSet(Set<Employee> departmentSet) {
		this.departmentSet = departmentSet;
	}

	public void setIdDepartment(Long idDepartment) {
		this.idDepartment = idDepartment;
	}

	public void setNameDepartment(String nameDepartment) {
		this.nameDepartment = nameDepartment;
	}

	public void setDateDepartment(LocalDateTime dateDepartment) {
		this.dateDepartment = dateDepartment;
	}

	@Override
	public String toString() {
		return "Department{" +
				"idDepartment=" + idDepartment +
				", nameDepartment='" + nameDepartment + '\'' +
				", dateDepartment=" + dateDepartment +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Department that = (Department) o;
		return idDepartment.equals(that.idDepartment) &&
				nameDepartment.equals(that.nameDepartment) &&
				dateDepartment.equals(that.dateDepartment);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idDepartment, nameDepartment, dateDepartment);
	}
}