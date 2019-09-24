package com.example.mainapp.model;

import com.example.mainapp.exeptions.NotFoundException;
import com.example.mainapp.entity.Slave;
import com.example.mainapp.repositories.CrudRepository;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

@Repository("slaveContext")
@RequestScope
public class SlaveContext implements CrudRepository<Slave> {

	public static int count = 0;

	private Class<Slave> clazz = Slave.class;

	private SessionFactory sessionFactory;

	private Logger logger;

	public SlaveContext() {
	}

	@Autowired
	public SlaveContext(
			@Qualifier("sessionFactory") SessionFactory sessionFactory,
			@Qualifier("LOG") Logger logger
	) {
		++count;
		this.sessionFactory = sessionFactory;
		this.logger = logger;
	}

	@Override
	public List<Slave> findAll(String sortOrder) throws Exception {
		try (Session session = this.sessionFactory.openSession()) {

			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

			CriteriaQuery<Slave> criteriaQuery = criteriaBuilder.createQuery(clazz);

			Root<Slave> criteriaRoot = criteriaQuery.from(clazz);

			Path<String> id = criteriaRoot.get(sortOrder);

			criteriaQuery
					.select(criteriaRoot)
					.orderBy(criteriaBuilder.asc(id))
			;

			Query<Slave> query = session.createQuery(criteriaQuery);

			List<Slave> tmp = query.list();

			logger.info(tmp.toString());

			return tmp;
		}
	}

	@Override
	public Slave insert(Slave slave) {
		try (Session session = this.sessionFactory.openSession()) {

			try {

				session.beginTransaction();

				long id = (long) session.save(slave);

				session.getTransaction().commit();

				Slave tmp = session.get(clazz, id);

				logger.info(tmp.toString());

				return tmp;

			} catch (Exception e) {

				logger.error(e.getMessage(), e);

				if (session.getTransaction().isActive()) {
					session.getTransaction().rollback();
				}

				throw e;
			}
		}
	}

	@Override
	public Slave delete(Slave item) {

		if (item.getIdSlave() == null) throw new NotFoundException("non id");

		try (Session session = this.sessionFactory.openSession()) {

			try {

				session.beginTransaction();

				Slave tmp = session.get(clazz, item.getIdSlave());

				if (Objects.nonNull(tmp)) {

					session.delete(tmp);

					session.getTransaction().commit();

					return tmp;

				} else {
					throw new NotFoundException("entity was not found");
				}

			} catch (Exception e) {

				logger.error(e.getMessage(), e);

				if (session.getTransaction().isActive()) {
					session.getTransaction().rollback();
					logger.info("rollback");
				}

				throw e;
			}
		}
	}

	@Override
	public Slave findById(Slave item) throws ObjectNotFoundException {
		return null;
	}

	@Override
	public Slave update(Slave item) {
		return null;
	}

	@Override
	public Slave patch(Slave item) throws NotFoundException {
		return null;
	}
}
