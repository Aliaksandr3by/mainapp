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
@Table(name = "employee", schema = "public")
@Access(AccessType.PROPERTY)
public class Employee implements Serializable {

	//TODO delete
	public boolean IsEmpty() {
		return this.getFirstName() == null
				&& this.getLastName() == null
				&& this.getGender() == null
				&& this.getJobTitle() == null
				&& this.getDepartment() == null;
	}

	public Employee patcherEmployee(Employee patch) {

		if (patch.getFirstName() != null) this.setFirstName(patch.getFirstName());
		if (patch.getLastName() != null) this.setLastName(patch.getLastName());
		if (patch.getGender() != null) this.setGender(patch.getGender());
		if (patch.getJobTitle() != null) this.setJobTitle(patch.getJobTitle());

		if (Objects.nonNull(patch.getDepartment()) && patch.getDepartment().getIdDepartment() != null) {
			this.getDepartment().patcherDepartment(patch.getDepartment());
		}

		return this;
	}

	public Employee() {
	}

	private Long employeeId;
	private String firstName;
	private String lastName;
	private Department department;
	private String jobTitle;
	private Gender gender;
	private Collection<EmployeeSlave> slaves = new ArrayList<>();

	public Employee(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Employee(String firstName, String lastName, Department departmentId, String jobTitle, Gender gender) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.department = departmentId;
		this.jobTitle = jobTitle;
		this.gender = gender;
	}

	public Employee(Long employeeId, String firstName, String lastName, Department department, String jobTitle, Gender gender, Collection<EmployeeSlave> slaves) {
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.department = department;
		this.jobTitle = jobTitle;
		this.gender = gender;
		this.slaves = slaves;
	}

	@ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "department_id", nullable = false)
	public Department getDepartment() {
		return department;
	}

	@JsonIgnore
	@OneToMany(
			fetch = FetchType.EAGER,
			mappedBy = "employee",
			cascade = {CascadeType.PERSIST, CascadeType.MERGE},
			orphanRemoval = true
	)
	public Collection<EmployeeSlave> getSlaves() {
		return slaves;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "employee_id_seq")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "employee_id", nullable = false, updatable = false)
	public Long getEmployeeId() {
		return employeeId;
	}

	@Column(name = "first_name", nullable = false)
	@Type(type = "text")
	public String getFirstName() {
		return firstName;
	}

	@Column(name = "last_name", nullable = false)
	@Type(type = "text")
	@Basic
	public String getLastName() {
		return lastName;
	}

	@Column(name = "job_title", nullable = false)
	@Type(type = "text")
	public String getJobTitle() {
		return jobTitle;
	}

	@Column(name = "gender", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	public Gender getGender() {
		return gender;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setSlaves(Collection<EmployeeSlave> slaves) {
		this.slaves = slaves;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Employee)) return false;
		Employee employee = (Employee) o;
		return employeeId.equals(employee.employeeId) &&
				firstName.equals(employee.firstName) &&
				lastName.equals(employee.lastName) &&
				department.equals(employee.department) &&
				jobTitle.equals(employee.jobTitle) &&
				gender == employee.gender;
	}

	@Override
	public int hashCode() {
		return Objects.hash(employeeId, firstName, lastName, department, jobTitle, gender);
	}

	@Override
	public String toString() {
		return "Employee{" +
				"employeeId=" + employeeId +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", department=" + department +
				", jobTitle='" + jobTitle + '\'' +
				", gender=" + gender +
				'}';
	}
}