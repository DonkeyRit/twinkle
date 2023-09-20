package com.github.donkeyrit.twinkle.bll.services.interfaces;

import com.github.donkeyrit.twinkle.dal.repositories.filters.CarQueryFilter;
import com.github.donkeyrit.twinkle.dal.models.CarBodyType;
import com.github.donkeyrit.twinkle.dal.models.MarkOfCar;
import com.github.donkeyrit.twinkle.bll.models.PagedResultBll;
import com.github.donkeyrit.twinkle.dal.models.Car;

import java.util.stream.Stream;
import java.util.Optional;
import java.util.List;

public interface CarService {
	
	Optional<List<String>> getExistingModelsByMark(MarkOfCar markSelected);
	List<MarkOfCar> getExistingMarks();
	Stream<CarBodyType> getCarBodyTypes();
	int getMaxPrice();
	PagedResultBll<Car> getPagedResult(CarQueryFilter filter);
	String isTaken(int carId);
}
