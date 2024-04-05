package com.github.donkeyrit.twinkle.dal.interfaces;

import com.github.donkeyrit.twinkle.dal.common.repositories.FilterableRepository;
import com.github.donkeyrit.twinkle.dal.common.specifications.PagedSpecification;
import com.github.donkeyrit.twinkle.dal.common.models.Page;
import com.github.donkeyrit.twinkle.dal.models.Car;
import com.github.donkeyrit.twinkle.dal.specifications.CarQuerySpecification;

public interface CarRepository<TPredicate> extends FilterableRepository<Car, TPredicate, CarQuerySpecification<TPredicate>> 
{
	Page<Car> getPagedResult(PagedSpecification<Car, TPredicate> filter);
	int getMaxPrice();
}
