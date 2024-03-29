package com.github.donkeyrit.twinkle.utils;

import com.github.donkeyrit.twinkle.dal.models.CarBodyType;
import com.github.donkeyrit.twinkle.dal.models.ModelOfCar;
import com.github.donkeyrit.twinkle.dal.models.MarkOfCar;
import com.github.donkeyrit.twinkle.dal.models.Country;
import com.github.donkeyrit.twinkle.dal.models.Car;

import java.sql.Date;

public class DaoFixture {

	public static Country createCountry(int id, String name) {
		Country country = new Country();
		country.setId(id);
		country.setCountryName(name);

		return country;
	}

	public static MarkOfCar createMarkOfCar(int id, String mark, Country country) {
		MarkOfCar markOfCar = new MarkOfCar();
		markOfCar.setId(id);
		markOfCar.setName(mark);
		markOfCar.setCountry(country);

		return markOfCar;
	}

	public static CarBodyType createCarBodyType(int id, String name) {
		CarBodyType carBodyType = new CarBodyType();
		carBodyType.setId(id);
		carBodyType.setType(name);

		return carBodyType;
	}

	public static ModelOfCar createModelOfCar(int id, String model, MarkOfCar markOfCar, CarBodyType carBodyType) {
		ModelOfCar modelOfCar = new ModelOfCar();
		modelOfCar.setId(id);
		modelOfCar.setModelName(model);
		modelOfCar.setMark(markOfCar);
		modelOfCar.setBodyTypeId(carBodyType);

		return modelOfCar;
	}

	public static Car createCar(int id, Date date, ModelOfCar modelOfCar, String info, int imageId, double cost)
	{
		Car car = new Car();
		car.setId(id);
		car.setModelYear(date);
		car.setModelOfCar(modelOfCar);
		car.setInfo(info);
		car.setImageId(imageId);
		car.setCost(cost);

		return car;
	}
}
