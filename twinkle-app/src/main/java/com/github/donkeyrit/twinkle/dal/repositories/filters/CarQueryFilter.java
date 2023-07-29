package com.github.donkeyrit.twinkle.dal.repositories.filters;

import com.github.donkeyrit.twinkle.dal.contracts.QueryFilter;
import com.github.donkeyrit.twinkle.dal.models.MarkOfCar;

import java.util.List;

public class CarQueryFilter implements QueryFilter  {
	
	//#region Fields

	private MarkOfCar selectedMark;
	private String selectedModel;
	private int selectedPrice;
	private List<String> selectedBodyTypes;

	//#endregion
	//#region Getters/Setters

	public MarkOfCar getSelectedMark() {
		return selectedMark;
	}
	public void setSelectedMark(MarkOfCar selectedMark) {
		this.selectedMark = selectedMark;
	}
	public String getSelectedModel() {
		return selectedModel;
	}
	public void setSelectedModel(String selectedModel) {
		this.selectedModel = selectedModel;
	}
	public int getSelectedPrice() {
		return selectedPrice;
	}
	public void setSelectedPrice(int selectedPrice) {
		this.selectedPrice = selectedPrice;
	}
	public List<String> getSelectedBodyTypes() {
		return selectedBodyTypes;
	}
	public void setSelectedBodyTypes(List<String> selectedBodyTypes) {
		this.selectedBodyTypes = selectedBodyTypes;
	}

	//#endregion
}
