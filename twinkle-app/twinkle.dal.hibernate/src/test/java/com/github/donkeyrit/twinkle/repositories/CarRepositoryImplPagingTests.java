package com.github.donkeyrit.twinkle.repositories;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.CarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.filters.CarQueryFilter;
import com.github.donkeyrit.twinkle.dal.repositories.CarRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.models.ModelOfCar;
import com.github.donkeyrit.twinkle.dal.models.filters.Paging;
import com.github.donkeyrit.twinkle.dal.models.utils.PagedResultDal;
import com.github.donkeyrit.twinkle.dal.models.Car;
import com.github.donkeyrit.twinkle.dal.models.CarBodyType;
import com.github.donkeyrit.twinkle.dal.models.Country;
import com.github.donkeyrit.twinkle.dal.models.MarkOfCar;
import com.github.donkeyrit.twinkle.utils.DateFixture;
import com.github.donkeyrit.twinkle.utils.DaoFixture;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;
import org.hibernate.cfg.Configuration;

import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;


public class CarRepositoryImplPagingTests extends Assertions {
	
	private static CarRepository carRepository;

	@BeforeClass
	public static void init() {
		EntityManagerFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		EntityManager session = sessionFactory.createEntityManager();

		carRepository = new CarRepositoryImpl(session);
	}

	@Test
	public void retrieveSecondPage_getPagedResult() {
		// Arrange
		CarQueryFilter carQueryFilter = new CarQueryFilter();
		Paging paging = new Paging(2, 5);
		carQueryFilter.setSelectedPrice(100000);
		carQueryFilter.setPaging(paging);

		CarBodyType sedanBodyType = DaoFixture.createCarBodyType(1, "Sedan");
		Country japan = DaoFixture.createCountry(1, "Japan");

		MarkOfCar hondaMark = DaoFixture.createMarkOfCar(2, "Honda", japan);
		MarkOfCar corollaMark = DaoFixture.createMarkOfCar(1, "Toyota", japan);

		ModelOfCar corollaCarModel = DaoFixture.createModelOfCar(2, "Corolla", corollaMark, sedanBodyType);
		ModelOfCar accordCarModel = DaoFixture.createModelOfCar(3, "Accord", hondaMark, sedanBodyType);
		ModelOfCar civicCarModel = DaoFixture.createModelOfCar(4, "Civic", hondaMark, sedanBodyType);

		List<Car> expectedResult = Arrays.asList(
				DaoFixture.createCar(6, DateFixture.getDate(2022, 0, 01), corollaCarModel, "Toyota Corolla 2022", 6, 24000),
				DaoFixture.createCar(7, DateFixture.getDate(2020, 0, 01), accordCarModel, "Honda Accord 2020", 7, 28000),
				DaoFixture.createCar(8, DateFixture.getDate(2021, 0, 01), accordCarModel, "Honda Accord 2021", 8, 29000),
				DaoFixture.createCar(9, DateFixture.getDate(2022, 0, 01), accordCarModel, "Honda Accord 2022", 9, 30000),
				DaoFixture.createCar(10, DateFixture.getDate(2020, 0, 01), civicCarModel, "Honda Civic 2020", 10, 25000));

		// Act
		PagedResultDal<Car> cars = carRepository.getPagedResult(carQueryFilter);

		// Assert
		assertThat(cars.getResult().toList()).hasSameElementsAs(expectedResult);
		assertEquals(cars.getTotalCount(), 100);
	}
}
