package com.github.donkeyrit.twinkle.dal.repositories.interfaces;

import com.github.donkeyrit.twinkle.dal.models.CarBodyType1;
import java.util.stream.Stream;

public interface CarBodyTypeRepository 
{
	Stream<CarBodyType1> getList();	
}
