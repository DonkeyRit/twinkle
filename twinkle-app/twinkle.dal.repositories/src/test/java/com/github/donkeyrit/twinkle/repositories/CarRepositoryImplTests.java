package com.github.donkeyrit.twinkle.repositories;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.CarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.filters.CarQueryFilter;
import com.github.donkeyrit.twinkle.dal.repositories.CarRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.models.Car;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;
import org.hibernate.cfg.Configuration;
import java.util.stream.Stream;
import org.junit.Test;

public class CarRepositoryImplTests {
	
	@Test
	public void filterByModel_getList(){
		//Arrange
		EntityManagerFactory sessionFactory = new Configuration()
            .configure()
            .buildSessionFactory();
        EntityManager session = sessionFactory.createEntityManager();

		CarRepository carRepository = new CarRepositoryImpl(session);
		CarQueryFilter carQueryFilter = new CarQueryFilter();
		carQueryFilter.setSelectedModel("Test");

		//Act
		Stream<Car> cars = carRepository.getList(carQueryFilter);

		//Assert

	}

}
