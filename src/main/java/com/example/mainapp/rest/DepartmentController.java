package com.example.mainapp.rest;

import com.example.mainapp.DAO.entity.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


	Logger logger = LoggerFactory.getLogger(Department.class);

	public DepartmentController() {
		logger.debug("Hello World");
	}

	@PersistenceUnit(unitName = "CRM")
	private EntityManagerFactory emf;

	@GetMapping(value = "/{id}")
	public List<Department> testCRM(@PathVariable("id") Integer id) throws Exception {

		logger.info("Это информационное сообщение!");

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

			Path<String> idDescription = criteriaRoot.get("idDescription");
			Path<String> cardNumber = criteriaRoot.get("cardNumber");

			criteriaQuery
					.select(criteriaRoot)
					.where(criteriaBuilder.equal(idDescription, id))
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
				logger.error(e.getMessage(), e);
			}
			throw e;
		}

	}

	public static void main(String[] args) {

	}

}
