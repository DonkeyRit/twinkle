package com.github.donkeyrit.twinkle.dal.repositories;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.CarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.filters.CarQueryFilter;
import com.github.donkeyrit.twinkle.dal.models.filters.Paging;
import com.github.donkeyrit.twinkle.dal.models.utils.PagedResultDal;
import com.github.donkeyrit.twinkle.dal.models.CarBodyType;
import com.github.donkeyrit.twinkle.dal.models.ModelOfCar;
import com.github.donkeyrit.twinkle.dal.models.MarkOfCar;
import com.github.donkeyrit.twinkle.dal.interfaces.BaseCrudRepository;
import com.github.donkeyrit.twinkle.dal.models.Car;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

public class CarRepositoryImpl extends BaseCrudRepository<Car, CarQueryFilter> implements CarRepository {

	@Inject
	public CarRepositoryImpl(EntityManager session) {
		super(session);
	}

	// TODO: Move this method to BaseCrudRepository
	public PagedResultDal<Car> getPagedResult(CarQueryFilter queryFilter) {

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		
		CriteriaQuery<Car> query = criteriaBuilder.createQuery(Car.class);
		Root<Car> root = query.from(Car.class);
		Predicate[] predicates = applyFilters(criteriaBuilder, queryFilter, root, criteriaBuilder);
		query.select(root).where(predicates);

		TypedQuery<Car> typedQuery = session.createQuery(query);
		Optional<Paging> paging = queryFilter.getPaging();
		paging.ifPresent(p -> AddPaging(typedQuery, p));

		// #region Total count

		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<Car> countRoot = countQuery.from(Car.class);
		Predicate[] countPredicates = applyFilters(criteriaBuilder, queryFilter, countRoot, criteriaBuilder);
		countQuery.select(criteriaBuilder.count(countRoot)).where(countPredicates);

		// #endregion
		return new PagedResultDal<Car>(typedQuery.getResultStream(), getTotalCount(countQuery));
	}

	// #region Create predicates

	private void AddSelectedModelPredicate(
		CriteriaBuilder criteriaBuilder, 
		CarQueryFilter queryFilter, 
		Root<Car> root,
		Join<Car, ModelOfCar> model, 
		List<Predicate> predicates
	) {

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
	) {
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
	) {
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

	// #endregion Create predicates

	private Predicate[] applyFilters(
		CriteriaBuilder criteriaBuilder, 
		CarQueryFilter queryFilter, 
		Root<Car> root, 
		CriteriaBuilder cb
	) {
		List<Predicate> predicateList = new ArrayList<>(4);

		Join<Car, ModelOfCar> model = null;
		Join<ModelOfCar, MarkOfCar> mark = null;
		Join<CarBodyType, ModelOfCar> carBodyType = null;

		AddSelectedModelPredicate(criteriaBuilder, queryFilter, root, model, predicateList);
		AddSelectedMarkPredicate(criteriaBuilder, queryFilter, root, model, mark, predicateList);
		AddSelectedPricePredicate(criteriaBuilder, queryFilter, root, predicateList);
		AddSelectedBodyTypesPredicate(criteriaBuilder, queryFilter, root, model, mark, carBodyType, predicateList);

		return predicateList.toArray(new Predicate[0]);
	}

	private <T> void AddPaging(TypedQuery<T> query, Paging paging) {
		int startIndex = (paging.getPageNumber() - 1) * paging.getPageSize();

		query.setFirstResult(startIndex);
		query.setMaxResults(paging.getPageSize());
	}

	private long getTotalCount(CriteriaQuery<Long> countQuery) {
		return session.createQuery(countQuery).getSingleResult();
	}

	@Override
	public int getMaxPrice() {
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Double> criteriaQuery = criteriaBuilder.createQuery(Double.class);
		Root<Car> carRoot = criteriaQuery.from(Car.class);

		criteriaQuery.select(criteriaBuilder.max(carRoot.get("cost")));

		TypedQuery<Double> typedQuery = session.createQuery(criteriaQuery);
		Double maxCost = typedQuery.getSingleResult();
		return (int) (maxCost.intValue() / 10000);
	}
}
