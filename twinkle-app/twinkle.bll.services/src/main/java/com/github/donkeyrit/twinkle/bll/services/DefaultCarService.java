package com.github.donkeyrit.twinkle.bll.services;

import com.github.donkeyrit.twinkle.dal.repositories.filters.CarQueryFilter;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.CarBodyTypeRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.ModelOfCarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.RentRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.MarkOfCarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.CarRepository;
import com.github.donkeyrit.twinkle.bll.models.PagedResultBll;
import com.github.donkeyrit.twinkle.bll.services.interfaces.CarService;
import com.github.donkeyrit.twinkle.dal.models.Car;
import com.github.donkeyrit.twinkle.dal.models.CarBodyType;
import com.github.donkeyrit.twinkle.dal.models.MarkOfCar;
import com.github.donkeyrit.twinkle.dal.models.utils.PagedResultDal;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Optional;
import java.util.List;

import com.google.inject.Inject;

public class DefaultCarService implements CarService {

	private final CarBodyTypeRepository carBodyTypeRepository;
	private final ModelOfCarRepository modelOfCarRepository;
	private final MarkOfCarRepository markOfCarRepository;
	private final RentRepository rentRepository;
	private final CarRepository carRepository;

	@Inject
	public DefaultCarService(
		CarBodyTypeRepository carBodyTypeRepository,
		ModelOfCarRepository modelOfCarRepository, 
		MarkOfCarRepository markOfCarRepository,
		RentRepository rentRepository,
		CarRepository carRepository
	) {
		this.carBodyTypeRepository = carBodyTypeRepository;
		this.modelOfCarRepository = modelOfCarRepository;
		this.markOfCarRepository = markOfCarRepository;
		this.rentRepository = rentRepository;
		this.carRepository = carRepository;
	}

	@Override
	public Optional<List<String>> getExistingModelsByMark(MarkOfCar markSelected) {
		if (markSelected.getId() > -1) {
			int markId = markSelected.getId();
			List<String> list = this.modelOfCarRepository.getListByMark(markId).map(model -> model.getModelName()).toList();
			return Optional.of(list);
		}

		return Optional.empty();
	}

	@Override
	public int getMaxPrice() {
		return this.carRepository.getMaxPrice();
	}

	@Override
	public List<MarkOfCar> getExistingMarks() {
		return this.markOfCarRepository.getList().collect(Collectors.toList());
	}

	@Override
	public Stream<CarBodyType> getCarBodyTypes() {
		return this.carBodyTypeRepository.getList();
	}

	@Override
	public PagedResultBll<Car> getPagedResult(CarQueryFilter filter) {
		PagedResultDal<Car> dal = this.carRepository.getPagedResult(filter);
		return new PagedResultBll<>(dal.getResult().toList(), dal.getTotalCount());
	}

	@Override
	public String isTaken(int carId) {
		return rentRepository.isTaken(carId) ? "lock" : "open";
	}
}
