package com.github.donkeyrit.twinkle.bll.services;

import com.github.donkeyrit.twinkle.dal.specifications.CarQuerySpecification;
import com.github.donkeyrit.twinkle.dal.specifications.ModelOfCarQuerySpecification;
import com.github.donkeyrit.twinkle.dal.interfaces.CarBodyTypeRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.ModelOfCarRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.MarkOfCarRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.CarRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.RentRepository;
import com.github.donkeyrit.twinkle.dal.models.CarBodyType;
import com.github.donkeyrit.twinkle.dal.models.MarkOfCar;
import com.github.donkeyrit.twinkle.dal.models.ModelOfCar;
import com.github.donkeyrit.twinkle.dal.models.Car;
import com.github.donkeyrit.twinkle.dal.common.models.Page;
import com.github.donkeyrit.twinkle.dal.common.specifications.QuerySpecification;
import com.github.donkeyrit.twinkle.bll.services.interfaces.CarService;
import com.github.donkeyrit.twinkle.bll.models.PagedResultBll;

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
			//TODO: use markid
			long markId = markSelected.getId();
			QuerySpecification<ModelOfCar> filter = new ModelOfCarQuerySpecification();
			List<String> list = this.modelOfCarRepository
				.getList(filter)
				.map(model -> model.getModelName())
				.toList();
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
		return this.markOfCarRepository.findAll().collect(Collectors.toList());
	}

	@Override
	public Stream<CarBodyType> getCarBodyTypes() {
		return this.carBodyTypeRepository.findAll();
	}

	@Override
	public PagedResultBll<Car> getPagedResult(CarQuerySpecification filter) {
		Page<Car> dal = this.carRepository.getPagedResult(filter);
		return new PagedResultBll<>(dal.getContent(), dal.getTotalElements());
	}

	@Override
	public String isTaken(long carId) {
		return rentRepository.isTaken(carId) ? "lock" : "open";
	}
}
