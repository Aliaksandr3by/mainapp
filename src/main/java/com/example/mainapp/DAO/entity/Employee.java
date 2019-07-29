package com.example.mainapp.DAO.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "employee")
public class Employee implements Serializable {

	public Employee() {
	}

	@Column(name = "employee_id", nullable = false)
	@GenericGenerator(name = "increment", strategy = "increment")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long employeeId;

	@Column(name = "first_name", nullable = false)
	@Type(type = "text")
	private String firstName;

	@Column(name = "last_name", nullable = false)
	@Type(type = "text")
	private String lastName;

	@Column(name = "department_id", unique = false, nullable = false)
	private Long departmentId;

	@Column(name = "job_title", nullable = false)
	@Type(type = "text")
	private String jobTitle;

	@Column(name = "gender", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private Gender gender;

	public Employee(String firstName, String lastName, Long departmentId, String jobTitle, Gender gender) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.departmentId = departmentId;
		this.jobTitle = jobTitle;
		this.gender = gender;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Employee employee = (Employee) o;
		return employeeId.equals(employee.employeeId) &&
				firstName.equals(employee.firstName) &&
				lastName.equals(employee.lastName) &&
				departmentId.equals(employee.departmentId) &&
				jobTitle.equals(employee.jobTitle) &&
				gender == employee.gender;
	}

	@Override
	public int hashCode() {
		return Objects.hash(employeeId, firstName, lastName, departmentId, jobTitle, gender);
	}
}