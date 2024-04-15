package com.github.donkeyrit.twinkle.dal.interfaces;

import com.github.donkeyrit.twinkle.dal.common.repositories.FilterableRepository;
import com.github.donkeyrit.twinkle.dal.common.specifications.PagedSpecification;
import com.github.donkeyrit.twinkle.dal.specifications.CarQuerySpecification;
import com.github.donkeyrit.twinkle.dal.common.models.Page;
import com.github.donkeyrit.twinkle.dal.models.Car;

public interface CarRepository extends FilterableRepository<Car, CarQuerySpecification> 
{
	Page<Car> getPagedResult(PagedSpecification<Car> filter);
	Double getMaxPrice();
}
