package com.github.donkeyrit.twinkle.dal.repositories;

import com.github.donkeyrit.twinkle.dal.interfaces.UserRepository;
import com.github.donkeyrit.twinkle.dal.models.User;
import com.github.donkeyrit.twinkle.dal.repositories.abstractions.HibernateFilterableRepository;
import com.github.donkeyrit.twinkle.dal.specifications.UserInfoSpecifciation;

import com.google.inject.Inject;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl extends HibernateFilterableRepository<User, UserInfoSpecifciation> implements UserRepository {

	@Inject
	public UserRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	protected Predicate[] toPredicates(CriteriaBuilder criteriaBuilder, Root<User> root, UserInfoSpecifciation specification) {
		List<Predicate> predicates = new ArrayList<>(2);

		if (specification.getLogin() != null) {
			predicates.add(criteriaBuilder.equal(root.get("login"), specification.getLogin()));
		}
		if (specification.getPasswordHash() != null) {
			predicates.add(criteriaBuilder.equal(root.get("password"), specification.getPasswordHash()));
		}

		return predicates.toArray(new Predicate[0]);
	}
}
