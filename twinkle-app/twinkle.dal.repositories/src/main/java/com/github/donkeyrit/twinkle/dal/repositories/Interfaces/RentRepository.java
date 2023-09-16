package com.github.donkeyrit.twinkle.dal.repositories.interfaces;

import com.github.donkeyrit.twinkle.dal.interfaces.CrudRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.QueryFilter;
import com.github.donkeyrit.twinkle.dal.models.Rent;

public interface RentRepository extends CrudRepository<Rent, QueryFilter>  {
	
	boolean isTaken(int carId);
}
