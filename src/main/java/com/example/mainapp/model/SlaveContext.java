package com.example.mainapp.model;

import com.example.mainapp.model.entity.Slave;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.springframework.web.context.annotation.RequestScope;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

@Named("slaveContext")
@RequestScope
public class SlaveContext {

	public static int count = 0;

	private SessionFactory sessionFactory;

	private Logger logger;

	public SlaveContext() {
	}

	@Inject
	public SlaveContext(@Named("sessionFactory") SessionFactory sessionFactory, @Named("LOG") Logger logger) {
		++count;
		this.sessionFactory = sessionFactory;
		this.logger = logger;
	}

	public List<Slave> getSlave(String sortOrder) throws Exception {
		try (Session session = this.sessionFactory.openSession()) {

			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

			CriteriaQuery<Slave> criteriaQuery = criteriaBuilder.createQuery(Slave.class);

			Root<Slave> criteriaRoot = criteriaQuery.from(Slave.class);

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

	public Slave createSlave(Slave slave) {
		try (Session session = this.sessionFactory.openSession()) {

			try {

				session.beginTransaction();

				long id = (long) session.save(slave);

				session.getTransaction().commit();

				Slave tmp = session.get(Slave.class, id);

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
}
