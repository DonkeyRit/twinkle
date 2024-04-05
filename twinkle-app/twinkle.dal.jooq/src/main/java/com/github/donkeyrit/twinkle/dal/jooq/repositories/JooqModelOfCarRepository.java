package com.github.donkeyrit.twinkle.dal.jooq.repositories;

import com.github.donkeyrit.twinkle.dal.jooq.abstractions.JooqFilterableRepository;
import com.github.donkeyrit.twinkle.dal.common.specifications.QuerySpecification;
import com.github.donkeyrit.twinkle.dal.interfaces.ModelOfCarRepository;
import com.github.donkeyrit.twinkle.dal.models.ModelOfCar;

import javax.sql.DataSource;
import org.jooq.Condition;

public class JooqModelOfCarRepository 
	extends JooqFilterableRepository<ModelOfCar, QuerySpecification<ModelOfCar, Condition>>
	implements ModelOfCarRepository<Condition> {

	public JooqModelOfCarRepository(DataSource dataSource) {
		super(dataSource);
	}

}
