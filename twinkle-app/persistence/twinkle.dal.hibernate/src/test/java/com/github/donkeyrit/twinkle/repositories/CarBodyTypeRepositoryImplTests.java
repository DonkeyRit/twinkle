package com.github.donkeyrit.twinkle.repositories;

import com.github.donkeyrit.twinkle.dal.interfaces.CarBodyTypeRepository;
import com.github.donkeyrit.twinkle.dal.repositories.CarBodyTypeRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.models.CarBodyType;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;
import org.hibernate.cfg.Configuration;

import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.tuple;

public class CarBodyTypeRepositoryImplTests extends Assertions {

	private static CarBodyTypeRepository carBodyTypeRepository;

	@BeforeClass
	public static void init() {
		EntityManagerFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		EntityManager session = sessionFactory.createEntityManager();

		carBodyTypeRepository = new CarBodyTypeRepositoryImpl(session);
	}

	@Test
	public void findById_returnsMatchingBodyType() {
		// Act
		CarBodyType bodyType = carBodyTypeRepository.findById(1);

		// Assert
		assertThat(bodyType.getId()).isEqualTo(1);
		assertThat(bodyType.getType()).isEqualTo("Sedan");
	}

	@Test
	public void findById_missingId_returnsNull() {
		// Act
		CarBodyType bodyType = carBodyTypeRepository.findById(-1);

		// Assert
		assertThat(bodyType).isNull();
	}

	@Test
	public void findAll_returnsEveryPredefinedBodyType() {
		// Act
		List<CarBodyType> bodyTypes = carBodyTypeRepository.findAll().toList();

		// Assert
		assertThat(bodyTypes).hasSize(100);
		assertThat(bodyTypes)
			.extracting(CarBodyType::getId, CarBodyType::getType)
			.contains(
				tuple(1, "Sedan"),
				tuple(4, "Convertible"),
				tuple(100, "Luxury Full-size Pickup Truck"));
	}
}
