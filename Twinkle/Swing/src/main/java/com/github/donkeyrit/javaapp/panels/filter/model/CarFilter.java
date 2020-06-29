package com.github.donkeyrit.javaapp.panels.filter.model;

import com.github.donkeyrit.javaapp.model.Car;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CarFilter implements ContentFilter<Car> {

    private Predicate<Car> markFiler;
    private Predicate<Car> modelFilter;
    private Predicate<Car> priceFilter;
    private Predicate<Car> bodyTypesFilter;


    public void addMarkFilter(String mark) {
        markFiler = car -> car.getMarkName().equals(mark);
    }

    public void addModelFilter(String model) {
        modelFilter = car -> car.getModelName().equals(model);
    }

    public void addPriceFilter(int price) {
        priceFilter = car -> car.getCost() < price * 1000;
    }

    public void addBodyTypesFilter(List<String> bodyTypes) {
        bodyTypesFilter = car -> bodyTypes.contains(car.getBodyTypeName());
    }

    @Override
    public Stream<Car> filter(Stream<Car> cars) {

        if (markFiler != null) {
            cars = cars.filter(markFiler);
        }
        if (modelFilter != null) {
            cars = cars.filter(modelFilter);
        }
        if (priceFilter != null) {
            cars = cars.filter(priceFilter);
        }
        if (bodyTypesFilter != null) {
            cars = cars.filter(bodyTypesFilter);
        }

        return cars;
    }
}
