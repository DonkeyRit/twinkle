package com.github.donkeyrit.twinkle.dal.repositories;

import com.github.donkeyrit.twinkle.dal.interfaces.CarRepository;
import com.github.donkeyrit.twinkle.dal.models.Car;
import com.github.donkeyrit.twinkle.dal.models.CarBodyType;
import com.github.donkeyrit.twinkle.dal.models.ModelOfCar;
import com.github.donkeyrit.twinkle.dal.repositories.abstractions.HibernateFilterableRepository;
import com.github.donkeyrit.twinkle.dal.specifications.CarQuerySpecification;

import com.google.inject.Inject;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class CarRepositoryImpl extends HibernateFilterableRepository<Car, CarQuerySpecification> implements CarRepository {

	@Inject
	public CarRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Double getMaxPrice() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Double> criteriaQuery = criteriaBuilder.createQuery(Double.class);
		Root<Car> carRoot = criteriaQuery.from(Car.class);
		criteriaQuery.select(criteriaBuilder.max(carRoot.get("cost")));

		Double maxCost = entityManager.createQuery(criteriaQuery).getSingleResult();
		return maxCost != null ? maxCost : 0;
	}

	@Override
	protected Predicate[] toPredicates(CriteriaBuilder criteriaBuilder, Root<Car> root, CarQuerySpecification specification) {
		List<Predicate> predicates = new ArrayList<>(4);

		Join<Car, ModelOfCar> model = null;

		if (specification.getSelectedModel().isPresent()) {
			model = root.join("modelOfCar");
			predicates.add(criteriaBuilder.equal(model.get("modelName"), specification.getSelectedModel().get()));
		}

		if (specification.getSelectedMark().isPresent()) {
			if (model == null) {
				model = root.join("modelOfCar");
			}
			Join<ModelOfCar, ?> mark = model.join("mark");
			predicates.add(criteriaBuilder.equal(mark.get("id"), specification.getSelectedMark().get().getId()));
		}

		if (specification.getSelectedPrice().isPresent()) {
			predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("cost"), specification.getSelectedPrice().get()));
		}

		if (!specification.getSelectedBodyTypes().isEmpty()) {
			if (model == null) {
				model = root.join("modelOfCar");
			}
			Join<ModelOfCar, CarBodyType> bodyType = model.join("bodyType");

			In<String> bodyTypeIn = criteriaBuilder.in(bodyType.get("type"));
			for (String selectedBodyType : specification.getSelectedBodyTypes()) {
				bodyTypeIn.value(selectedBodyType);
			}
			predicates.add(bodyTypeIn);
		}

		return predicates.toArray(new Predicate[0]);
	}
}
