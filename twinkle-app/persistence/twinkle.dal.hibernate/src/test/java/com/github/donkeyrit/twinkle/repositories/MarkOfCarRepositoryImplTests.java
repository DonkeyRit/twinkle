package com.github.donkeyrit.twinkle.repositories;

import com.github.donkeyrit.twinkle.dal.interfaces.MarkOfCarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.MarkOfCarRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.models.MarkOfCar;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;
import org.hibernate.cfg.Configuration;

import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class MarkOfCarRepositoryImplTests extends Assertions {

	private static MarkOfCarRepository markOfCarRepository;

	@BeforeClass
	public static void init() {
		EntityManagerFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		EntityManager session = sessionFactory.createEntityManager();

		markOfCarRepository = new MarkOfCarRepositoryImpl(session);
	}

	@Test
	public void findById_returnsMarkWithItsCountry() {
		// Act
		MarkOfCar mark = markOfCarRepository.findById(1);

		// Assert
		assertThat(mark.getName()).isEqualTo("Toyota");
		// Hibernate resolves the full object graph, unlike jOOQ's raw-FK mapping.
		assertThat(mark.getCountry().getCountryName()).isEqualTo("Japan");
	}

	@Test
	public void findById_missingId_returnsNull() {
		// Act
		MarkOfCar mark = markOfCarRepository.findById(-1);

		// Assert
		assertThat(mark).isNull();
	}

	@Test
	public void findAll_returnsEveryPredefinedMark() {
		// Act
		List<MarkOfCar> marks = markOfCarRepository.findAll().toList();

		// Assert
		assertThat(marks).hasSize(100);
		assertThat(marks)
			.extracting(MarkOfCar::getName)
			.contains("Toyota", "Honda", "Ferrari");
	}
}
