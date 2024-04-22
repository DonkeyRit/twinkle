package com.github.donkeyrit.twinkle.dal.repositories.interfaces;

import com.github.donkeyrit.twinkle.dal.models.ModelOfCar1;
import java.util.stream.Stream;

public interface ModelOfCarRepository 
{
	Stream<ModelOfCar1> getListByMark(int markId);
}
