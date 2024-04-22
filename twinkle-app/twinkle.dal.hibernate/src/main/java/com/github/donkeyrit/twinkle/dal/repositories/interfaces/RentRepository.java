package com.github.donkeyrit.twinkle.dal.repositories.interfaces;

import com.github.donkeyrit.twinkle.dal.interfaces.CrudRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.QueryFilter;
import com.github.donkeyrit.twinkle.dal.models.Rent1;

public interface RentRepository extends CrudRepository<Rent1, QueryFilter>  {
	
	boolean isTaken(int carId);
}
