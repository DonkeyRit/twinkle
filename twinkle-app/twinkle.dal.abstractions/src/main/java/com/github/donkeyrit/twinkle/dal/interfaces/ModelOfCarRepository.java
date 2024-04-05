package com.github.donkeyrit.twinkle.dal.interfaces;

import com.github.donkeyrit.twinkle.dal.common.repositories.FilterableRepository;
import com.github.donkeyrit.twinkle.dal.common.specifications.QuerySpecification;
import com.github.donkeyrit.twinkle.dal.models.ModelOfCar;

public interface ModelOfCarRepository<TPredicate> extends FilterableRepository<ModelOfCar, TPredicate, QuerySpecification<ModelOfCar, TPredicate>>
{

}
