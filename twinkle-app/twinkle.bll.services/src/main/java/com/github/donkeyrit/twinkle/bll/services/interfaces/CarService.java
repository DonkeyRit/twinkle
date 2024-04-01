package com.github.donkeyrit.twinkle.bll.services.interfaces;

import com.github.donkeyrit.twinkle.dal.specifications.CarQuerySpecification;
import com.github.donkeyrit.twinkle.dal.models.CarBodyType;
import com.github.donkeyrit.twinkle.dal.models.MarkOfCar;
import com.github.donkeyrit.twinkle.dal.models.Car;
import com.github.donkeyrit.twinkle.bll.models.PagedResultBll;

import java.util.stream.Stream;
import java.util.Optional;
import java.util.List;

public interface CarService {
	
	Optional<List<String>> getExistingModelsByMark(MarkOfCar markSelected);
	List<MarkOfCar> getExistingMarks();
	Stream<CarBodyType> getCarBodyTypes();
	int getMaxPrice();
	PagedResultBll<Car> getPagedResult(CarQuerySpecification filter);
	String isTaken(int carId);
}
