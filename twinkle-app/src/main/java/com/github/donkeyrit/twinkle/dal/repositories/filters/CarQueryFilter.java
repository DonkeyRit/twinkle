package com.github.donkeyrit.twinkle.dal.repositories.filters;

import com.github.donkeyrit.twinkle.dal.contracts.QueryFilter;
import com.github.donkeyrit.twinkle.dal.models.MarkOfCar;

import java.util.Optional;
import java.util.List;

public class CarQueryFilter implements QueryFilter  {
	
	//#region Fields

	private Optional<MarkOfCar> selectedMark;
	private Optional<String> selectedModel;
	private Optional<Integer> selectedPrice;
	private List<String> selectedBodyTypes;

	//#endregion
	//#region Getters/Setters

	public Optional<MarkOfCar> getSelectedMark() {
		return selectedMark;
	}
	public void setSelectedMark(MarkOfCar selectedMark) {
		this.selectedMark = Optional.of(selectedMark);
	}
	public Optional<String> getSelectedModel() {
		return selectedModel;
	}
	public void setSelectedModel(String selectedModel) {
		this.selectedModel = Optional.of(selectedModel);
	}
	public Optional<Integer> getSelectedPrice() {
		return selectedPrice;
	}
	public void setSelectedPrice(int selectedPrice) {
		this.selectedPrice = Optional.of(selectedPrice);
	}
	public List<String> getSelectedBodyTypes() {
		return selectedBodyTypes;
	}
	public void setSelectedBodyTypes(List<String> selectedBodyTypes) {
		this.selectedBodyTypes = selectedBodyTypes;
	}

	//#endregion
}
