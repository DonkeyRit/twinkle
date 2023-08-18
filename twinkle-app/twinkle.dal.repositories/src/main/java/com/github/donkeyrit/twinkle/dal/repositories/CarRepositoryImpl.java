package com.github.donkeyrit.twinkle.dal.repositories;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.CarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.filters.CarQueryFilter;
import com.github.donkeyrit.twinkle.dal.contracts.BaseCrudRepository;
import com.github.donkeyrit.twinkle.dal.models.ModelOfCar;
import com.github.donkeyrit.twinkle.dal.models.Car;
import com.github.donkeyrit.twinkle.dal.models.CarBodyType;
import com.github.donkeyrit.twinkle.dal.models.MarkOfCar;

import jakarta.persistence.criteria.CriteriaBuilder.In;
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

		Join<Car, ModelOfCar> model = null;
		Join<ModelOfCar, MarkOfCar> mark = null;
		Join<CarBodyType, ModelOfCar> carBodyType = null;

		Root<Car> root = criteriaQuery.from(Car.class);
		List<Predicate> predicates = new ArrayList<Predicate>(4);

		AddSelectedModelPredicate(criteriaBuilder, queryFilter, root, model, predicates);
		AddSelectedMarkPredicate(criteriaBuilder, queryFilter, root, model, mark, predicates);
		AddSelectedPricePredicate(criteriaBuilder, queryFilter, root, predicates);
		AddSelectedBodyTypesPredicate(criteriaBuilder, queryFilter, root, model, mark, carBodyType, predicates);
		
		criteriaQuery.select(root).where(predicates.toArray(new Predicate[0]));

		TypedQuery<Car> query = session.createQuery(criteriaQuery);
		return query.getResultStream();
	}

	private void AddSelectedModelPredicate(
		CriteriaBuilder criteriaBuilder, 
		CarQueryFilter queryFilter, 
		Root<Car> root,
		Join<Car, ModelOfCar> model, 
		List<Predicate> predicates) {

		if (queryFilter.getSelectedModel().isPresent()) {

			String modelName = queryFilter.getSelectedModel().get();
			model = root.join("modelOfCar");

			Predicate eqModelName = criteriaBuilder.equal(model.get("modelName"), modelName);
			predicates.add(eqModelName);
		}
	}

	private void AddSelectedMarkPredicate(
		CriteriaBuilder criteriaBuilder, 
		CarQueryFilter queryFilter, 
		Root<Car> root,
		Join<Car, ModelOfCar> model,
		Join<ModelOfCar, MarkOfCar> mark,
		List<Predicate> predicates
	) {

		if (queryFilter.getSelectedMark().isPresent()) {

			int markId = queryFilter.getSelectedMark().get().getId();
			if (model == null)
				model = root.join("modelOfCar");
			mark = model.join("mark");

			Predicate eqMarkId = criteriaBuilder.equal(mark.get("id"), markId);
			predicates.add(eqMarkId);
		}
	}

	private void AddSelectedPricePredicate(
		CriteriaBuilder criteriaBuilder, 
		CarQueryFilter queryFilter, 
		Root<Car> root,
		List<Predicate> predicates
	)
	{
		if (queryFilter.getSelectedPrice().isPresent()) {
			double selectedPrice = queryFilter.getSelectedPrice().get();
			Predicate leCostPredicate = criteriaBuilder.lessThanOrEqualTo(root.get("cost"), selectedPrice);

			predicates.add(leCostPredicate);
		}
	}

	private void AddSelectedBodyTypesPredicate(
		CriteriaBuilder criteriaBuilder, 
		CarQueryFilter queryFilter, 
		Root<Car> root,
		Join<Car, ModelOfCar> model,
		Join<ModelOfCar, MarkOfCar> mark,
		Join<CarBodyType, ModelOfCar> carBodyType,
		List<Predicate> predicates
	)
	{
		if (!queryFilter.getSelectedBodyTypes().isEmpty()) {

			if (model == null)
				model = root.join("modelOfCar");
			carBodyType = model.join("bodyType");

			In<String> selectedCarBodyTypesPredicate = criteriaBuilder.in(carBodyType.get("type"));
			for (String selectedCarBodyType : queryFilter.getSelectedBodyTypes()) {
				selectedCarBodyTypesPredicate.value(selectedCarBodyType);
			}
			predicates.add(selectedCarBodyTypesPredicate);
		}
	}
}
