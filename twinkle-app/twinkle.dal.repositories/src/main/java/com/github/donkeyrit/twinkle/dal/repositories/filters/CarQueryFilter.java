package com.github.donkeyrit.twinkle.dal.repositories.filters;

import com.github.donkeyrit.twinkle.dal.models.filters.Paging;
import com.github.donkeyrit.twinkle.dal.models.MarkOfCar;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

public class CarQueryFilter extends PagingFilter  {
	
	//#region Fields

	private Optional<MarkOfCar> selectedMark;
	private Optional<String> selectedModel;
	private Optional<Double> selectedPrice;
	private List<String> selectedBodyTypes;

	public CarQueryFilter(Paging paging) {
		this.selectedMark = Optional.empty();
		this.selectedModel = Optional.empty();
		this.selectedPrice = Optional.empty();
		this.selectedBodyTypes = new ArrayList<String>(0);
		this.setPaging(paging);
	}

	public CarQueryFilter() {
		this.selectedMark = Optional.empty();
		this.selectedModel = Optional.empty();
		this.selectedPrice = Optional.empty();
		this.selectedBodyTypes = new ArrayList<String>(0);
	}

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
	public Optional<Double> getSelectedPrice() {
		return selectedPrice;
	}
	public void setSelectedPrice(double selectedPrice) {
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
