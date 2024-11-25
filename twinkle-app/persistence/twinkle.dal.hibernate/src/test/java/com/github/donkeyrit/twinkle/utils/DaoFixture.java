package com.github.donkeyrit.twinkle.utils;

import com.github.donkeyrit.twinkle.dal.models.CarBodyType1;
import com.github.donkeyrit.twinkle.dal.models.ModelOfCar1;
import com.github.donkeyrit.twinkle.dal.models.MarkOfCar1;
import com.github.donkeyrit.twinkle.dal.models.Country1;
import com.github.donkeyrit.twinkle.dal.models.Car1;

import java.sql.Date;

public class DaoFixture {

	public static Country1 createCountry(int id, String name) {
		Country1 country = new Country1();
		country.setId(id);
		country.setCountryName(name);

		return country;
	}

	public static MarkOfCar1 createMarkOfCar(int id, String mark, Country1 country) {
		MarkOfCar1 markOfCar = new MarkOfCar1();
		markOfCar.setId(id);
		markOfCar.setName(mark);
		markOfCar.setCountry(country);

		return markOfCar;
	}

	public static CarBodyType1 createCarBodyType(int id, String name) {
		CarBodyType1 carBodyType = new CarBodyType1();
		carBodyType.setId(id);
		carBodyType.setType(name);

		return carBodyType;
	}

	public static ModelOfCar1 createModelOfCar(int id, String model, MarkOfCar1 markOfCar, CarBodyType1 carBodyType) {
		ModelOfCar1 modelOfCar = new ModelOfCar1();
		modelOfCar.setId(id);
		modelOfCar.setModelName(model);
		modelOfCar.setMark(markOfCar);
		modelOfCar.setBodyTypeId(carBodyType);

		return modelOfCar;
	}

	public static Car1 createCar(int id, Date date, ModelOfCar1 modelOfCar, String info, int imageId, double cost)
	{
		Car1 car = new Car1();
		car.setId(id);
		car.setModelYear(date);
		car.setModelOfCar(modelOfCar);
		car.setInfo(info);
		car.setImageId(imageId);
		car.setCost(cost);

		return car;
	}
}
