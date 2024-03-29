package com.github.donkeyrit.twinkle.dal.repositories.interfaces;

import com.github.donkeyrit.twinkle.dal.repositories.filters.CarQueryFilter;
import com.github.donkeyrit.twinkle.dal.interfaces.CrudRepository;
import com.github.donkeyrit.twinkle.dal.models.Car;
import com.github.donkeyrit.twinkle.dal.models.utils.PagedResultDal;

public interface CarRepository extends CrudRepository<Car, CarQueryFilter> 
{
	int getMaxPrice();
	PagedResultDal<Car> getPagedResult(CarQueryFilter filter);
}
