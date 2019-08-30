package com.example.mainapp.model.entity;

import com.example.mainapp.exeptions.NotFoundException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "employee", schema = "public")
@Access(AccessType.PROPERTY)
public class Employee implements Serializable {


	public boolean IsEmpty() {

		return this.getFirstName() == null
				&& this.getLastName() == null
				&& this.getGender() == null
				&& this.getJobTitle() == null
				&& this.getDepartment() == null;
	}

	public Employee employeeUpdater(Employee patch) {

		if (this.equals(patch)) throw new NotFoundException("Object is equals ");//FIXME

		if (patch.getFirstName() != null) this.setFirstName(patch.getFirstName());
		if (patch.getLastName() != null) this.setLastName(patch.getLastName());
		if (patch.getGender() != null) this.setGender(patch.getGender());
		if (patch.getJobTitle() != null) this.setJobTitle(patch.getJobTitle());
		if (patch.getDepartment().getIdDepartment() != null) {
			this.getDepartment().DepartmentUpdater(patch.getDepartment());
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
	private Collection<Slave> slaves;

	public Employee(String firstName, String lastName, Department departmentId, String jobTitle, Gender gender) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.department = departmentId;
		this.jobTitle = jobTitle;
		this.gender = gender;
	}

	@JsonIgnore
	@ManyToMany(mappedBy = "employees", fetch = FetchType.EAGER)
	public Collection<Slave> getSlaves() {
		return slaves;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "employee_id_seq")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "employee_id", nullable = false)
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

	//	@Column(name = "department_id", unique = false, nullable = false)
//	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name = "department_id", nullable = false, referencedColumnName = "id_department")
	public Department getDepartment() {
		return department;
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

	public void setSlaves(Collection<Slave> orders) {
		this.slaves = orders;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Employee employee = (Employee) o;
		return Objects.equals(employeeId, employee.employeeId) &&
				Objects.equals(firstName, employee.firstName) &&
				Objects.equals(lastName, employee.lastName) &&
				Objects.equals(department, employee.department) &&
				Objects.equals(jobTitle, employee.jobTitle) &&
				gender == employee.gender;
	}

	@Override
	public int hashCode() {
		return Objects.hash(employeeId, firstName, lastName, department, jobTitle, gender);
	}
}