package com.github.donkeyrit.twinkle.dal.repositories.abstractions;

import com.github.donkeyrit.twinkle.dal.common.models.Identifiable;
import com.github.donkeyrit.twinkle.dal.common.models.Page;
import com.github.donkeyrit.twinkle.dal.common.models.Paging;
import com.github.donkeyrit.twinkle.dal.common.repositories.FilterableRepository;
import com.github.donkeyrit.twinkle.dal.common.specifications.QuerySpecification;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Hibernate counterpart of {@code JooqFilterableRepository} (twinkle.dal.jooq). Subclasses only
 * need to translate a {@link QuerySpecification} into JPA {@link Predicate}s.
 */
public abstract class HibernateFilterableRepository<T extends Identifiable, TSpecification extends QuerySpecification<T>>
		extends HibernateGenericRepository<T> implements FilterableRepository<T, TSpecification> {

	protected HibernateFilterableRepository(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Stream<T> getList(TSpecification querySpecification) {
		return buildQuery(querySpecification, Optional.empty()).getResultStream();
	}

	@Override
	public Optional<T> get(TSpecification querySpecification) {
		return buildQuery(querySpecification, Optional.empty()).getResultStream().findFirst();
	}

	@Override
	public Page<T> getPagedResult(TSpecification filter, Optional<Paging> paging) {
		List<T> content = buildQuery(filter, paging).getResultList();
		long totalElements = countMatching(filter);

		int pageNumber = paging.map(Paging::getPageNumber).orElse(1);
		int pageSize = paging.map(Paging::getPageSize).orElse(Math.max(content.size(), 1));

		return new Page<>(content, pageNumber, pageSize, totalElements);
	}

	protected abstract Predicate[] toPredicates(CriteriaBuilder criteriaBuilder, Root<T> root, TSpecification specification);

	private TypedQuery<T> buildQuery(TSpecification specification, Optional<Paging> paging) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<T> root = criteriaQuery.from(entityClass);
		criteriaQuery.select(root).where(toPredicates(criteriaBuilder, root, specification));

		TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
		paging.ifPresent(p -> {
			int startIndex = (p.getPageNumber() - 1) * p.getPageSize();
			query.setFirstResult(startIndex);
			query.setMaxResults(p.getPageSize());
		});
		return query;
	}

	private long countMatching(TSpecification specification) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<T> root = countQuery.from(entityClass);
		countQuery.select(criteriaBuilder.count(root)).where(toPredicates(criteriaBuilder, root, specification));

		return entityManager.createQuery(countQuery).getSingleResult();
	}
}
