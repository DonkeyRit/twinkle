package com.github.donkeyrit.javaapp.panels.filter.model;

import com.github.donkeyrit.javaapp.model.Car;

import java.util.stream.Stream;

public class NullFilter implements ContentFilter<Car> {
    @Override
    public Stream<Car> filter(Stream<Car> content) {
        return content;
    }
}
