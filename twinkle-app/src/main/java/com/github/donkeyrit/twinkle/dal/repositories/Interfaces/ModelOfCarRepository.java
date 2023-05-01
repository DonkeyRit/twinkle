package com.github.donkeyrit.twinkle.dal.repositories.interfaces;

import com.github.donkeyrit.twinkle.dal.models.ModelOfCar;
import java.util.stream.Stream;

public interface ModelOfCarRepository 
{
	Stream<ModelOfCar> getListByMark(int markId);
}
