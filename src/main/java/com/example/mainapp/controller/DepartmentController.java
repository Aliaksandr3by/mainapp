package com.example.mainapp.controller;

import com.example.mainapp.model.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;


@RestController
@RequestMapping(path = "/department", produces = "application/json")
@CrossOrigin(origins = "*")
public class DepartmentController {

	public DepartmentController() {
	}

//	@PersistenceUnit(unitName = "CRM")
//	private EntityManagerFactory _emf;
//
//	@PersistenceContext
//	private EntityManager _em;

	@Autowired
	@Qualifier("entityManager")
	private EntityManager em;

	@GetMapping(value = "/{id}")
	public List<Department> getDepartment(@PathVariable("id") Integer id) throws Exception {

		EntityTransaction entityTransaction = null;

		try {

			Class<Department> clazz = Department.class;

			entityTransaction = em.getTransaction();

			entityTransaction.begin();

			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

			CriteriaQuery<Department> criteriaQuery = criteriaBuilder.createQuery(clazz);

			Root<Department> criteriaRoot = criteriaQuery.from(clazz);

			Path<String> departmentId = criteriaRoot.get("idDepartment");

			criteriaQuery
					.select(criteriaRoot)
					.where(criteriaBuilder.equal(departmentId, id))
			;

			TypedQuery<Department> query = em.createQuery(criteriaQuery);

			List<Department> tmp = query.getResultList();

//			var findData = em.find(Cards.class, 3);

//			System.out.println(tmp.toString());

			entityTransaction.commit();

			return tmp;

		} catch (Exception e) {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			throw e;
		}

	}

	public static void main(String[] args) {

	}

}
