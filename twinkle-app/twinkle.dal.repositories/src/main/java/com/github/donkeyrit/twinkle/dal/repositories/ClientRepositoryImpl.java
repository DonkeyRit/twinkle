package com.github.donkeyrit.twinkle.dal.repositories;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.ClientRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.BaseCrudRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.QueryFilter;
import com.github.donkeyrit.twinkle.dal.models.Client;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import com.google.inject.Inject;
import java.util.Optional;

public class ClientRepositoryImpl extends BaseCrudRepository<Client, QueryFilter> implements ClientRepository {

	@Inject
	public ClientRepositoryImpl(EntityManager session) {
		super(session);
	}

	@Override
	public Optional<Client> getByUserId(int userId) {
		TypedQuery<Client> query = session.createQuery("SELECT u FROM Client u WHERE u.userId = :userId", Client.class);
        query.setParameter("userId", userId);
		return query.getResultStream().findFirst();
	}
}
