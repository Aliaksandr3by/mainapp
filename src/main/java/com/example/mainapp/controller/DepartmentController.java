package com.example.mainapp.controller;

import com.example.mainapp.model.entity.Department;
import com.example.mainapp.model.entity.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

	@PersistenceUnit(unitName = "CRM") //FIXME
	private EntityManagerFactory emf;

	@GetMapping(value = "/{id}")
	public List<Department> getDepartment(@PathVariable("id") Integer id) throws Exception {

		EntityManager em = null;
		EntityTransaction entityTransaction = null;

		try {

			Class<Department> clazz = Department.class;

			//emf = Persistence.createEntityManagerFactory("CRM");

			em = this.emf.createEntityManager();
//em.persist(null);
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

			emf.close();

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
