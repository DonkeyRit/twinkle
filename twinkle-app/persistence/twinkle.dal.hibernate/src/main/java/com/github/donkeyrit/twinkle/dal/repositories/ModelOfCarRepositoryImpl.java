package com.github.donkeyrit.twinkle.dal.repositories;

import com.github.donkeyrit.twinkle.dal.common.specifications.QuerySpecification;
import com.github.donkeyrit.twinkle.dal.interfaces.ModelOfCarRepository;
import com.github.donkeyrit.twinkle.dal.models.ModelOfCar;
import com.github.donkeyrit.twinkle.dal.repositories.abstractions.HibernateFilterableRepository;

import com.google.inject.Inject;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ModelOfCarRepositoryImpl
		extends HibernateFilterableRepository<ModelOfCar, QuerySpecification<ModelOfCar>>
		implements ModelOfCarRepository {

	@Inject
	public ModelOfCarRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	protected Predicate[] toPredicates(CriteriaBuilder criteriaBuilder, Root<ModelOfCar> root, QuerySpecification<ModelOfCar> specification) {
		// QuerySpecification<ModelOfCar> carries no filter fields yet - mirrors JooqModelOfCarRepository.
		return new Predicate[0];
	}
}
