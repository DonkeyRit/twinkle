package com.github.donkeyrit.twinkle.dal.repositories;

import com.github.donkeyrit.twinkle.dal.contracts.BaseCrudRepository;
import com.github.donkeyrit.twinkle.dal.models.Rent;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.RentRepository;

import jakarta.persistence.EntityManager;

public class RentRepositoryImpl extends BaseCrudRepository<Rent> implements RentRepository {

	public RentRepositoryImpl(EntityManager session) {
		super(session);
	}
	
}
