package com.github.donkeyrit.twinkle.dal.repositories;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.CarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.filters.CarQueryFilter;
import com.github.donkeyrit.twinkle.dal.contracts.BaseCrudRepository;
import com.github.donkeyrit.twinkle.dal.models.Car;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.stream.Stream;

public class CarRepositoryImpl extends BaseCrudRepository<Car, CarQueryFilter> implements CarRepository {

	public CarRepositoryImpl(EntityManager session) {
		super(session);
	}

	@Override
	public Stream<Car> getList(CarQueryFilter queryFilter) {

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);

		criteriaQuery.from(Car.class);

		if(queryFilter.getSelectedMark().isPresent()){

		}

		if(queryFilter.getSelectedModel().isPresent()){

		}

		if(queryFilter.getSelectedPrice().isPresent()){

		}

		if(!queryFilter.getSelectedBodyTypes().isEmpty()){
			
		}


		TypedQuery<Car> query = session.createQuery(criteriaQuery);
		return query.getResultStream();
	}
}
