package com.github.donkeyrit.twinkle.dal.repositories;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.ClientRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.BaseCrudRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.QueryFilter;
import com.github.donkeyrit.twinkle.dal.models.Client1;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import com.google.inject.Inject;
import java.util.Optional;

public class ClientRepositoryImpl extends BaseCrudRepository<Client1, QueryFilter> implements ClientRepository {

	@Inject
	public ClientRepositoryImpl(EntityManager session) {
		super(session);
	}

	@Override
	public Optional<Client1> getByUserId(int userId) {
		TypedQuery<Client1> query = session.createQuery("SELECT u FROM Client u WHERE u.userId = :userId", Client1.class);
        query.setParameter("userId", userId);
		return query.getResultStream().findFirst();
	}
}
