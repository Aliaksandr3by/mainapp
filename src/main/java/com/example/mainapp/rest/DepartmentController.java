package com.example.mainapp.rest;

import com.example.mainapp.DAO.entity.Department;
import org.springframework.web.bind.annotation.*;

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
	public List<Department> testCRM(@PathVariable("id") Integer id) throws Exception {

		EntityManager em = null;
		EntityTransaction entityTransaction = null;

		try {

			Class<Department> clazz = Department.class;

			emf = Persistence.createEntityManagerFactory("CRM");

			em = emf.createEntityManager();

			entityTransaction = em.getTransaction();

			entityTransaction.begin();

			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

			CriteriaQuery<Department> criteriaQuery = criteriaBuilder.createQuery(clazz);

			Root<Department> criteriaRoot = criteriaQuery.from(clazz);

			Path<String> departmentId = criteriaRoot.get("department_id");

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
