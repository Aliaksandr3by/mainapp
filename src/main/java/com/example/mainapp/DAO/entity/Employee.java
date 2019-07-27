package com.example.mainapp.DAO.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "employee")
public class Employee implements Serializable {

	public Employee() {
	}

	@Id
	@Column(name = "employee_id", unique = true)
//	@GenericGenerator(name = "increment", strategy = "increment")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long employeeId;

	@Column(name = "first_name")
	@Type(type = "text")
	private String firstName;

	@Column(name = "last_name")
	@Type(type = "text")
	private String lastName;

	@Column(name = "department_id", unique = false)
	private Long departmentId;

	@Column(name = "job_title")
	@Type(type = "text")
	private String jobTitle;

	@Column(name = "gender")
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
}