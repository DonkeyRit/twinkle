package com.github.donkeyrit.twinkle.dal.models;

import com.github.donkeyrit.twinkle.dal.common.models.Identifiable;

public class MarkOfCar implements Identifiable {

    private int id;
    private String name;
    private Country country;
	private int countryId;

	//#region Constructors

    public MarkOfCar() {
    }

    public MarkOfCar(int id, String name, int countryId) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
    }

	public MarkOfCar(int id, String name, Country country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

	//#endregion

    //#region Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	//#endregion
}
