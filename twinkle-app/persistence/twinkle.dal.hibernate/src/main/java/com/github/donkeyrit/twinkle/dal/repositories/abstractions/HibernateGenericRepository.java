package com.github.donkeyrit.twinkle.dal.repositories.abstractions;

import com.github.donkeyrit.twinkle.dal.common.models.Identifiable;
import com.github.donkeyrit.twinkle.dal.common.repositories.GenericRepository;

import jakarta.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.stream.Stream;

/**
 * Hibernate counterpart of {@code JooqGenericRepository} (twinkle.dal.jooq): both implement
 * the same {@link GenericRepository} contract from twinkle.dal.abstractions, so either one can
 * be bound behind it and swapped via `persistence.strategy` without touching callers.
 */
public abstract class HibernateGenericRepository<T extends Identifiable> implements GenericRepository<T> {

	private static final Logger logger = LoggerFactory.getLogger(HibernateGenericRepository.class);

	protected final EntityManager entityManager;
	protected final Class<T> entityClass;

	@SuppressWarnings("unchecked")
	protected HibernateGenericRepository(EntityManager entityManager) {
		this.entityManager = entityManager;

		Type genericSuperclass = getClass().getGenericSuperclass();
		ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
		this.entityClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}

	@Override
	public T findById(int id) {
		return entityManager.find(entityClass, id);
	}

	@Override
	public Stream<T> findAll() {
		return entityManager
			.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
			.getResultStream();
	}

	@Override
	public boolean save(T entity) {
		return runInTransaction("saving", entity, entityManager::persist);
	}

	@Override
	public boolean update(T entity) {
		return runInTransaction("updating", entity, entityManager::merge);
	}

	@Override
	public boolean delete(T entity) {
		return runInTransaction("deleting", entity, entityManager::remove);
	}

	private boolean runInTransaction(String action, T entity, java.util.function.Consumer<T> work) {
		try {
			entityManager.getTransaction().begin();
			work.accept(entity);
			entityManager.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			logger.error("Exception occurred in {} while {} entity with id {}",
				getClass().getName(), action, entity.getId(), ex);
			return false;
		}
	}
}
