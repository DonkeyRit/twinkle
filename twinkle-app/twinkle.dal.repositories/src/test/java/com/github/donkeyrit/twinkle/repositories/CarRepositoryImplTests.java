package com.github.donkeyrit.twinkle.repositories;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.CarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.filters.CarQueryFilter;
import com.github.donkeyrit.twinkle.dal.repositories.CarRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.models.Car;
import com.github.donkeyrit.twinkle.dal.models.CarBodyType;
import com.github.donkeyrit.twinkle.dal.models.Country;
import com.github.donkeyrit.twinkle.dal.models.MarkOfCar;
import com.github.donkeyrit.twinkle.dal.models.ModelOfCar;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.hibernate.cfg.Configuration;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.junit.Test;

public class CarRepositoryImplTests extends Assertions {

	@Test
	public void filterByModel_getList() {
		// Arrange
		EntityManagerFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		EntityManager session = sessionFactory.createEntityManager();

		CarRepository carRepository = new CarRepositoryImpl(session);
		CarQueryFilter carQueryFilter = new CarQueryFilter();
		carQueryFilter.setSelectedModel("Camry");

		List<Car> expectedResult = new ArrayList<>(3);

		Country country = new Country();
		country.setId(1);
		country.setCountryName("Japan");

		MarkOfCar markOfCar = new MarkOfCar();
		markOfCar.setId(1);
		markOfCar.setName("Toyota");
		markOfCar.setCountry(country);

		CarBodyType carBodyType = new CarBodyType();
		carBodyType.setId(1);
		carBodyType.setType("Sedan");

		ModelOfCar modelOfCar = new ModelOfCar();
		modelOfCar.setId(1);
		modelOfCar.setModelName("Camry");
		modelOfCar.setMark(markOfCar);
		modelOfCar.setBodyTypeId(carBodyType);

		Car first = new Car();
		first.setId(1);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1988);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		first.setModelYear(getDate(2020, 0, 01));
		first.setModelOfCar(modelOfCar);
		first.setInfo("Toyota Camry 2020");
		first.setImageId(1);
		first.setCost(25000);
		expectedResult.add(first);

		Car second = new Car();
		second.setId(2);
		second.setModelYear(getDate(2021, 0, 01));
		second.setModelOfCar(modelOfCar);
		second.setInfo("Toyota Camry 2021");
		second.setImageId(2);
		second.setCost(26000);
		expectedResult.add(second);

		Car third = new Car();
		third.setId(3);
		third.setModelOfCar(modelOfCar);
		third.setModelYear(getDate(2022, 0, 01));
		third.setInfo("Toyota Camry 2022");
		third.setImageId(3);
		third.setCost(27000);
		expectedResult.add(third);

		// Act
		List<Car> cars = carRepository.getList(carQueryFilter).toList();

		// Assert
		assertThat(cars).hasSameElementsAs(expectedResult);
	}

	private static Date getDate(int year, int month, int day) {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);

		return new Date(cal.getTimeInMillis());
	}

}
