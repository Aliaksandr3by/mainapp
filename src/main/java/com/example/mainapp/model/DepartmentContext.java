package com.example.mainapp.model;

import com.example.mainapp.entity.Department;
import com.example.mainapp.exeptions.NotFoundException;
import com.example.mainapp.exeptions.NotImplementedException;
import com.example.mainapp.repositories.CrudRepository;
import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

@Repository
@RequestScope
public class DepartmentContext implements CrudRepository<Department> {

	private Class<Department> clazz = Department.class;

	private Logger logger;

	private EntityManagerFactory emf;

	@PostConstruct
	public void init() {
		logger.info("departmentContext was initialized");
	}

	@PreDestroy
	public void destroy() {
		logger.info("departmentContext was destroyed");
	}

	public DepartmentContext() {
	}

	@Autowired
	public DepartmentContext(Logger logger, EntityManagerFactory managerFactory) {
		this.logger = logger;
		this.emf = managerFactory;
	}

	@Override
	public List<Department> findAll(String sortOrder) {

		EntityManager em = null;
		EntityTransaction entityTransaction = null;

		try {
			em = emf.createEntityManager();

			entityTransaction = em.getTransaction();

			entityTransaction.begin();

			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

			CriteriaQuery<Department> criteriaQuery = criteriaBuilder.createQuery(clazz);

			Root<Department> criteriaRoot = criteriaQuery.from(clazz);

			Path<String> departmentId = criteriaRoot.get(sortOrder);

			criteriaQuery
					.select(criteriaRoot)
					.orderBy(criteriaBuilder.asc(departmentId))
			;

			TypedQuery<Department> query = em.createQuery(criteriaQuery);

			List<Department> tmp = query.getResultList();

			entityTransaction.commit();

			return tmp;

		} catch (Exception e) {
			if (Objects.nonNull(entityTransaction) && entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			throw e;
		} finally {
			if (em != null && em.isOpen()) em.close();
		}

	}

	@Override
	public Department findById(Department item) throws ObjectNotFoundException {

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
					.where(criteriaBuilder.equal(departmentId, item.getIdDepartment()))
			;

			TypedQuery<Department> query = em.createQuery(criteriaQuery);

			Department tmp = query.getSingleResult();

			entityTransaction.commit();

			return tmp;

		} catch (Exception e) {
			if (Objects.nonNull(entityTransaction) && entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			throw e;
		} finally {
			if (em != null && em.isOpen()) em.close();
		}

	}

	@Override
	public Department insert(Department item) {

		EntityManager em = null;
		EntityTransaction entityTransaction = null;

		try {

			em = emf.createEntityManager();

			entityTransaction = em.getTransaction();

			entityTransaction.begin();

			em.persist(item);

			entityTransaction.commit();

			return item;

		} catch (Exception e) {
			if (Objects.nonNull(entityTransaction) && entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			throw e;
		} finally {
			if (em != null && em.isOpen()) em.close();
		}
	}

	@Override
	public Department delete(Department item) {

		EntityManager em = null;
		EntityTransaction entityTransaction = null;

		try {

			em = emf.createEntityManager();

			entityTransaction = em.getTransaction();

			entityTransaction.begin();

			Department tmp = em.find(clazz, item.getIdDepartment());

			if (Objects.nonNull(tmp)) {
				em.remove(tmp);
				entityTransaction.commit();
			}

			return tmp;

		} catch (Exception e) {
			if (Objects.nonNull(entityTransaction) && entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			throw e;
		} finally {
			if (em != null && em.isOpen()) em.close();
		}
	}

	@Override
	public Department update(Department item) {
		throw new NotImplementedException("Method is not implemented");
	}

	@Override
	public Department patch(Department item) throws NotFoundException {
		throw new NotImplementedException("Method is not implemented");
	}
}
