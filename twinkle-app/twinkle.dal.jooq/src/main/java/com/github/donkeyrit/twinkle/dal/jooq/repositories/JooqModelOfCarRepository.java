package com.github.donkeyrit.twinkle.dal.jooq.repositories;

import java.util.stream.Stream;

import com.github.donkeyrit.twinkle.dal.common.specifications.QuerySpecification;
import com.github.donkeyrit.twinkle.dal.interfaces.ModelOfCarRepository;
import com.github.donkeyrit.twinkle.dal.models.ModelOfCar;

public class JooqModelOfCarRepository implements ModelOfCarRepository {

	@Override
	public Stream<ModelOfCar> getList(QuerySpecification<ModelOfCar> querySpecification) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getList'");
	}

	@Override
	public ModelOfCar findById(Long id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'findById'");
	}

	@Override
	public Stream<ModelOfCar> findAll() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'findAll'");
	}

	@Override
	public boolean save(ModelOfCar o) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'save'");
	}

	@Override
	public boolean delete(ModelOfCar o) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'delete'");
	}

	@Override
	public boolean update(ModelOfCar o) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'update'");
	}
	
}
