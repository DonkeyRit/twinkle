package com.github.donkeyrit.twinkle.dal.repositories;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.CarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.filters.CarQueryFilter;
import com.github.donkeyrit.twinkle.dal.contracts.BaseCrudRepository;
import com.github.donkeyrit.twinkle.dal.models.ModelOfCar;
import com.github.donkeyrit.twinkle.dal.models.Car;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.List;

public class CarRepositoryImpl extends BaseCrudRepository<Car, CarQueryFilter> implements CarRepository {

	public CarRepositoryImpl(EntityManager session) {
		super(session);
	}

	@Override
	public Stream<Car> getList(CarQueryFilter queryFilter) {

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);

		Root<Car> root = criteriaQuery.from(Car.class);
		List<Predicate> predicates = new ArrayList<Predicate>(4);

		if(queryFilter.getSelectedMark().isPresent()){

			//root.join(root.get("null"), null)

			//criteriaBuilder.equal(root.get("modelOfCar"), queryFilter.getSelectedMark().get().getId());
		}

		if(queryFilter.getSelectedModel().isPresent()){

			String modelName = queryFilter.getSelectedModel().get();
			Join<Car, ModelOfCar> model = root.join("modelOfCar");

			Predicate pd = criteriaBuilder.equal(model.get("modelName"), modelName);
			predicates.add(pd);
		}

		if(queryFilter.getSelectedPrice().isPresent()){
			Predicate gtCostPredicate = criteriaBuilder.lessThan(
				root.get("cost"), 
				queryFilter.getSelectedPrice().get());

			predicates.add(gtCostPredicate);
		}

		if(!queryFilter.getSelectedBodyTypes().isEmpty()){

		}

		criteriaQuery
			.select(root)
			.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Car> query = session.createQuery(criteriaQuery);
		return query.getResultStream();
	}
}
