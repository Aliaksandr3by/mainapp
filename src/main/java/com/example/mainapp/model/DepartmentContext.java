package com.example.mainapp.model;

import com.example.mainapp.model.entity.Department;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.annotation.RequestScope;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository("departmentContext")
@RequestScope
public class DepartmentContext {

	Class<Department> clazz = Department.class;

	private Logger logger;

	@PersistenceUnit(unitName = "CRM") //FIXME
	private EntityManagerFactory _emf;

	private EntityManagerFactory emf;

	@Autowired
	public DepartmentContext(
			@Qualifier("LOG") Logger logger,
			@Qualifier("entityManagerFactory") EntityManagerFactory emf
	) {
		this.logger = logger;
		this.emf = emf;
	}

	public List<Department> getDepartments() throws Exception {

		EntityManager em = null;
		EntityTransaction entityTransaction = null;

		try {
			em = emf.createEntityManager();

			entityTransaction = em.getTransaction();

			entityTransaction.begin();

			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

			CriteriaQuery<Department> criteriaQuery = criteriaBuilder.createQuery(clazz);

			Root<Department> criteriaRoot = criteriaQuery.from(clazz);

			Path<String> departmentId = criteriaRoot.get("idDepartment");

			criteriaQuery
					.select(criteriaRoot)
					.orderBy(criteriaBuilder.asc(departmentId))
			;

			TypedQuery<Department> query = em.createQuery(criteriaQuery);

			List<Department> tmp = query.getResultList();

			entityTransaction.commit();

			em.close();

			return tmp;

		} catch (Exception e) {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			throw e;
		}

	}

	public List<Department> getDepartmentById(@PathVariable("id") Integer id) throws Exception {

		EntityManager em = null;
		EntityTransaction entityTransaction = null;

		try {

			em = emf.createEntityManager();

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

			entityTransaction.commit();

			em.close();

			return tmp;

		} catch (Exception e) {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			throw e;
		}

	}

	public Department deleteDepartment(Department item)  {

		EntityManager em = null;
		EntityTransaction entityTransaction = null;

		try {

			em = emf.createEntityManager();

			entityTransaction = em.getTransaction();

			entityTransaction.begin();

			Department tmp = em.find(clazz, item.getIdDepartment());

			em.remove(tmp);

			entityTransaction.commit();

			em.close();

			return tmp;

		} catch (Exception e) {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			throw e;
		}

	}
}
