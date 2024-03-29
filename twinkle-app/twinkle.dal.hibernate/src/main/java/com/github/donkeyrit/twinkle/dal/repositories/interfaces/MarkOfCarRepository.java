package com.github.donkeyrit.twinkle.dal.repositories.interfaces;

import com.github.donkeyrit.twinkle.dal.models.MarkOfCar;
import java.util.stream.Stream;

public interface MarkOfCarRepository 
{
	Stream<MarkOfCar> getList();
}
