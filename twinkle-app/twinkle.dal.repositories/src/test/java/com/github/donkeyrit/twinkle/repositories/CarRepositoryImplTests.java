package com.github.donkeyrit.twinkle.repositories;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.CarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.filters.CarQueryFilter;
import com.github.donkeyrit.twinkle.dal.repositories.CarRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.models.CarBodyType;
import com.github.donkeyrit.twinkle.dal.models.ModelOfCar;
import com.github.donkeyrit.twinkle.dal.models.MarkOfCar;
import com.github.donkeyrit.twinkle.dal.models.Car;
import com.github.donkeyrit.twinkle.utils.DaoFixture;
import com.github.donkeyrit.twinkle.utils.DateFixture;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;
import org.hibernate.cfg.Configuration;

import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class CarRepositoryImplTests extends Assertions {

	private static CarRepository carRepository;

	@BeforeClass
	public static void init() {
		EntityManagerFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		EntityManager session = sessionFactory.createEntityManager();

		carRepository = new CarRepositoryImpl(session);
	}

	@Test
	public void filterByModel_getList() {
		// Arrange
		CarQueryFilter carQueryFilter = new CarQueryFilter();
		carQueryFilter.setSelectedModel("Camry");

		ModelOfCar modelOfCar = DaoFixture.createModelOfCar(1, "Camry",
				DaoFixture.createMarkOfCar(1, "Toyota", DaoFixture.createCountry(1, "Japan")),
				DaoFixture.createCarBodyType(1, "Sedan"));

		List<Car> expectedResult = Arrays.asList(
				DaoFixture.createCar(1, DateFixture.getDate(2020, 0, 01), modelOfCar, "Toyota Camry 2020", 1, 25000),
				DaoFixture.createCar(2, DateFixture.getDate(2021, 0, 01), modelOfCar, "Toyota Camry 2021", 2, 26000),
				DaoFixture.createCar(3, DateFixture.getDate(2022, 0, 01), modelOfCar, "Toyota Camry 2022", 3, 27000));

		// Act
		List<Car> cars = carRepository.getList(carQueryFilter).toList();

		// Assert
		assertThat(cars).hasSameElementsAs(expectedResult);
	}

	@Test
	public void filterByMark_getList() {
		// Arrange
		MarkOfCar hondaMark = DaoFixture.createMarkOfCar(2, "Honda", DaoFixture.createCountry(1, "Japan"));

		CarQueryFilter carQueryFilter = new CarQueryFilter();
		carQueryFilter.setSelectedMark(hondaMark);

		CarBodyType sedanBodyType = DaoFixture.createCarBodyType(1, "Sedan");
		ModelOfCar accordCarModel = DaoFixture.createModelOfCar(3, "Accord", hondaMark, sedanBodyType);
		ModelOfCar civicCarModel = DaoFixture.createModelOfCar(4, "Civic", hondaMark, sedanBodyType);

		List<Car> expectedResult = Arrays.asList(
				DaoFixture.createCar(7, DateFixture.getDate(2020, 0, 01), accordCarModel, "Honda Accord 2020", 7,28000),
				DaoFixture.createCar(8, DateFixture.getDate(2021, 0, 01), accordCarModel, "Honda Accord 2021", 8,29000),
				DaoFixture.createCar(9, DateFixture.getDate(2022, 0, 01), accordCarModel, "Honda Accord 2022", 9,30000),
				DaoFixture.createCar(10, DateFixture.getDate(2020, 0, 01), civicCarModel, "Honda Civic 2020", 10,25000),
				DaoFixture.createCar(11, DateFixture.getDate(2021, 0, 01), civicCarModel, "Honda Civic 2021", 11,26000),
				DaoFixture.createCar(12, DateFixture.getDate(2022, 0, 01), civicCarModel, "Honda Civic 2022", 12,27000));

		// Act
		List<Car> cars = carRepository.getList(carQueryFilter).toList();

		// Assert
		assertThat(cars).hasSameElementsAs(expectedResult);
	}

	@Test
	public void filterByPrice_getList() {
		// Arrange
		double selectedPrice = 22000;
		CarQueryFilter carQueryFilter = new CarQueryFilter();
		carQueryFilter.setSelectedPrice(selectedPrice);

		CarBodyType sedanBodyType = DaoFixture.createCarBodyType(1, "Sedan");
		MarkOfCar kiaMark = DaoFixture.createMarkOfCar(13, "Kia", DaoFixture.createCountry(5, "Sweden"));
		MarkOfCar toyotaMark = DaoFixture.createMarkOfCar(1, "Toyota", DaoFixture.createCountry(1, "Japan"));

		ModelOfCar corollaCarModel = DaoFixture.createModelOfCar(2, "Corolla", toyotaMark, sedanBodyType);
		ModelOfCar civicCarModel = DaoFixture.createModelOfCar(32, "Optima", kiaMark, sedanBodyType);

		List<Car> expectedResult = Arrays.asList(
				DaoFixture.createCar(4, DateFixture.getDate(2020, 0, 01), corollaCarModel, "Toyota Corolla 2020", 4, selectedPrice),
				DaoFixture.createCar(93, DateFixture.getDate(2020, 0, 01), civicCarModel, "Honda Civic 2020", 93, selectedPrice));

		// Act
		List<Car> cars = carRepository.getList(carQueryFilter).toList();

		// Assert
		assertThat(cars).hasSameElementsAs(expectedResult);
	}
}
