package com.github.donkeyrit.twinkle.repositories;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.CarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.filters.CarQueryFilter;
import com.github.donkeyrit.twinkle.dal.repositories.CarRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.models.ModelOfCar;
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

	@BeforeClass public static void init(){
		EntityManagerFactory sessionFactory = new Configuration()
			.configure()
			.buildSessionFactory();
		EntityManager session = sessionFactory.createEntityManager();

		carRepository = new CarRepositoryImpl(session);
	}

	@Test public void filterByModel_getList() {
		// Arrange
		
		CarQueryFilter carQueryFilter = new CarQueryFilter();
		carQueryFilter.setSelectedModel("Camry");

		//List<Car> expectedResult = new ArrayList<>(3);
		ModelOfCar modelOfCar = DaoFixture.createModelOfCar(
			1, 
			"Camry", 
			DaoFixture.createMarkOfCar(
				1, 
				"Toyota",
				DaoFixture.createCountry(1, "Japan")), 
			DaoFixture.createCarBodyType(1, "Sedan"));

		List<Car> expectedResult = Arrays.asList(
			DaoFixture.createCar(
				1, 
				DateFixture.getDate(2020, 0, 01), 
				modelOfCar, 
				"Toyota Camry 2020", 
				1, 
				25000),
			DaoFixture.createCar(
				2, 
				DateFixture.getDate(2021, 0, 01), 
				modelOfCar, 
				"Toyota Camry 2021", 
				2, 
				26000),
			DaoFixture.createCar(
				3, 
				DateFixture.getDate(2022, 0, 01), 
				modelOfCar, 
				"Toyota Camry 2022", 
				3, 
				27000)
		);

		// Act
		List<Car> cars = carRepository.getList(carQueryFilter).toList();

		// Assert
		assertThat(cars).hasSameElementsAs(expectedResult);
	}
}
