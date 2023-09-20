package com.github.donkeyrit.twinkle.repositories;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.CarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.filters.CarQueryFilter;
import com.github.donkeyrit.twinkle.dal.repositories.CarRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.models.CarBodyType;
import com.github.donkeyrit.twinkle.dal.models.Country;
import com.github.donkeyrit.twinkle.dal.models.ModelOfCar;
import com.github.donkeyrit.twinkle.dal.models.utils.PagedResultDal;
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

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

public class CarRepositoryImplFilteringTests extends Assertions {

	private static CarRepository carRepository;

	@BeforeClass
	public static void init() {
		EntityManagerFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		EntityManager session = sessionFactory.createEntityManager();

		carRepository = new CarRepositoryImpl(session);
	}

	@Test
	public void filterByModel_getPagedResult() {
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
		PagedResultDal<Car> cars = carRepository.getPagedResult(carQueryFilter);
		List<Car> actualResult = cars.getResult().toList();

		// Assert
		assertThat(actualResult).hasSameElementsAs(expectedResult);
		assertEquals(cars.getTotalCount(), 3);
	}

	@Test
	public void filterByMark_getPagedResult() {
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
		PagedResultDal<Car> cars = carRepository.getPagedResult(carQueryFilter);
		List<Car> actualResult = cars.getResult().toList();

		// Assert
		assertThat(actualResult).hasSameElementsAs(expectedResult);
		assertEquals(cars.getTotalCount(), 6);
	}

	@Test
	public void filterByPrice_getPagedResult() {
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
		PagedResultDal<Car> cars = carRepository.getPagedResult(carQueryFilter);
		List<Car> actualResult = cars.getResult().toList();

		// Assert
		assertThat(actualResult).hasSameElementsAs(expectedResult);
		assertEquals(cars.getTotalCount(), 2);
	}

	@Test
	public void filterByCarBodyTypes_getPagedResult() {
		// Arrange
		List<String> selectedCarBodyTypes = Arrays.asList("Convertible", "Truck");
		CarQueryFilter carQueryFilter = new CarQueryFilter();
		carQueryFilter.setSelectedBodyTypes(selectedCarBodyTypes);

		CarBodyType convertibleBodyType = DaoFixture.createCarBodyType(4, "Convertible");
		Country usa = DaoFixture.createCountry(3, "USA");
		MarkOfCar fordMark = DaoFixture.createMarkOfCar(4, "Ford", usa);
		MarkOfCar chevroletMark = DaoFixture.createMarkOfCar(5, "Chevrolet", usa);

		ModelOfCar f150CarModel = DaoFixture.createModelOfCar(7, "F-150", fordMark, convertibleBodyType);
		ModelOfCar silveradoCarModel = DaoFixture.createModelOfCar(10, "Silverado", chevroletMark, convertibleBodyType);

		List<Car> expectedResult = Arrays.asList(
				DaoFixture.createCar(19, DateFixture.getDate(2020, 0, 01), f150CarModel, "Nissan Altima 2020", 19, 27000),
				DaoFixture.createCar(20, DateFixture.getDate(2021, 0, 01), f150CarModel, "Nissan Altima 2021", 20, 28000),
				DaoFixture.createCar(21, DateFixture.getDate(2022, 0, 01), f150CarModel, "Nissan Altima 2022", 21, 29000),
				DaoFixture.createCar(28, DateFixture.getDate(2020, 0, 01), silveradoCarModel, "Mercedes-Benz C-Class 2020", 28, 38000),
				DaoFixture.createCar(29, DateFixture.getDate(2021, 0, 01), silveradoCarModel, "Mercedes-Benz C-Class 2021", 29, 39000),
				DaoFixture.createCar(30, DateFixture.getDate(2022, 0, 01), silveradoCarModel, "Mercedes-Benz C-Class 2022", 30, 40000));
		// Act
		PagedResultDal<Car> cars = carRepository.getPagedResult(carQueryFilter);
		List<Car> actualResult = cars.getResult().toList();

		// Assert
		assertThat(actualResult).hasSameElementsAs(expectedResult);
		assertEquals(cars.getTotalCount(), 6);
	}

	@Test
	public void filterByAllFields_getPagedResult() {
		// Arrange
		CarBodyType suvBodyType = DaoFixture.createCarBodyType(2, "SUV");
		Country germany = DaoFixture.createCountry(2, "Germany");
		MarkOfCar mercedesBenzMark = DaoFixture.createMarkOfCar(6, "Mercedes-Benz", germany);
		ModelOfCar eClassCarModel = DaoFixture.createModelOfCar(11, "E-Class", mercedesBenzMark, suvBodyType);
		
		CarQueryFilter carQueryFilter = new CarQueryFilter();
		carQueryFilter.setSelectedMark(mercedesBenzMark);
		carQueryFilter.setSelectedModel(eClassCarModel.getModelName());
		carQueryFilter.setSelectedPrice(31000);
		carQueryFilter.setSelectedBodyTypes(Arrays.asList(suvBodyType.getType()));

		List<Car> expectedResult = Arrays.asList(
			DaoFixture.createCar(31, DateFixture.getDate(2020, 0, 01), eClassCarModel, "Acura TLX 2020", 31, 30000),
				DaoFixture.createCar(32, DateFixture.getDate(2021, 0, 01), eClassCarModel, "Acura TLX 2021", 32, 31000));
		// Act
		PagedResultDal<Car> cars = carRepository.getPagedResult(carQueryFilter);
		List<Car> actualResult = cars.getResult().toList();

		// Assert
		assertThat(actualResult).hasSameElementsAs(expectedResult);
		assertEquals(cars.getTotalCount(), 2);
	}
}
