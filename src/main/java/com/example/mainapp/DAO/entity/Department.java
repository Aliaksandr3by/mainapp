package com.example.mainapp.DAO.entity;

import com.example.mainapp.exeptions.NotFoundException;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "department", schema = "public")
public class Department implements Serializable {


	@Column(name = "department_id", nullable = false)
	@GenericGenerator(name = "increment", strategy = "increment")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long departmentId;

	@Column(name = "name_department", nullable = false)
	@Type(type = "text")
	private String nameDepartment;

	@Column(name = "date_department", nullable = false)
	@Type(type = "LocalDateTime")
	private LocalDateTime dateDepartment;


}