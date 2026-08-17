package com.github.donkeyrit.twinkle.dal.repositories;

import com.github.donkeyrit.twinkle.dal.interfaces.ClientRepository;
import com.github.donkeyrit.twinkle.dal.models.Client;
import com.github.donkeyrit.twinkle.dal.repositories.abstractions.HibernateGenericRepository;

import com.google.inject.Inject;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Optional;

public class ClientRepositoryImpl extends HibernateGenericRepository<Client> implements ClientRepository {

	@Inject
	public ClientRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Optional<Client> getByUserId(int userId) {
		TypedQuery<Client> query = entityManager.createQuery(
			"SELECT c FROM Client c WHERE c.userId = :userId", Client.class);
		query.setParameter("userId", userId);
		return query.getResultStream().findFirst();
	}
}
