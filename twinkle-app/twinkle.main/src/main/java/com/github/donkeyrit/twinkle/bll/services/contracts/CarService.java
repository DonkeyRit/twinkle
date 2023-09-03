package com.github.donkeyrit.twinkle.bll.services.contracts;

import com.github.donkeyrit.twinkle.dal.models.CarBodyType;
import com.github.donkeyrit.twinkle.dal.models.MarkOfCar;

import java.util.stream.Stream;
import java.util.Optional;
import java.util.List;

public interface CarService {
	
	Optional<List<String>> getExistingModelsByMark(MarkOfCar markSelected);
	List<MarkOfCar> getExistingMarks();
	Stream<CarBodyType> getCarBodyTypes();
	int getMaxPrice();
}
