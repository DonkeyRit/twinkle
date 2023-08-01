package com.github.donkeyrit.twinkle.dal.repositories.interfaces;

import com.github.donkeyrit.twinkle.dal.contracts.CrudRepository;
import com.github.donkeyrit.twinkle.dal.contracts.QueryFilter;
import com.github.donkeyrit.twinkle.dal.models.Rent;

public interface RentRepository extends CrudRepository<Rent, QueryFilter>  {
	
	boolean isTaken(int carId);
}
