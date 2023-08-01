package com.github.donkeyrit.twinkle.dal.repositories;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.RentRepository;
import com.github.donkeyrit.twinkle.dal.contracts.BaseCrudRepository;
import com.github.donkeyrit.twinkle.dal.contracts.QueryFilter;
import com.github.donkeyrit.twinkle.dal.models.Rent;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class RentRepositoryImpl extends BaseCrudRepository<Rent, QueryFilter> implements RentRepository {

	public RentRepositoryImpl(EntityManager session) {
		super(session);
	}

	@Override
	public boolean isTaken(int carId) {

		TypedQuery<Rent> query = session.createQuery(
			"SELECT u FROM Rent u WHERE u.idCar = :carId ORDER BY u.endDate", Rent.class);
        query.setParameter("carId", carId);
		query.setMaxResults(1);
		
		if(query.getFirstResult() == 0){
			return false;
		}

		Rent lastRent = query.getSingleResult();
		return lastRent.getEndDate() == null;
	}
}
