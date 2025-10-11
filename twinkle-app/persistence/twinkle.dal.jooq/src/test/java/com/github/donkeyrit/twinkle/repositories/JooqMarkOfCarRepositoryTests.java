package com.github.donkeyrit.twinkle.repositories;

import com.github.donkeyrit.twinkle.dal.interfaces.MarkOfCarRepository;
import com.github.donkeyrit.twinkle.dal.jooq.repositories.JooqMarkOfCarRepository;
import com.github.donkeyrit.twinkle.dal.models.MarkOfCar;
import com.github.donkeyrit.twinkle.utils.JooqTestSupport;

import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class JooqMarkOfCarRepositoryTests extends Assertions {

	private static MarkOfCarRepository markOfCarRepository;

	@BeforeClass
	public static void init() {
		markOfCarRepository = new JooqMarkOfCarRepository(JooqTestSupport.createContext());
	}

	@Test
	public void findById_returnsMarkWithRawCountryId() {
		// Act
		MarkOfCar mark = markOfCarRepository.findById(1);

		// Assert
		assertThat(mark.getName()).isEqualTo("Toyota");
		// Unlike Hibernate, jOOQ has no notion of the object graph: only the raw
		// id_country FK is populated, `country` itself stays null.
		assertThat(mark.getCountryId()).isEqualTo(1);
		assertThat(mark.getCountry()).isNull();
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
