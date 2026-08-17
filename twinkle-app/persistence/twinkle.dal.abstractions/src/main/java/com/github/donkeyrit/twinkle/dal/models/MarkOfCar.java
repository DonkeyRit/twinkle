package com.github.donkeyrit.twinkle.dal.models;

import com.github.donkeyrit.twinkle.dal.common.models.Identifiable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "mark")
public class MarkOfCar implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "mark_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_country")
    private Country country;

    // The raw FK is kept in sync with `country` by Hibernate (insertable/updatable = false
    // so `country` stays the single writable owner of the relation) and is populated
    // directly by jOOQ, which has no notion of the object graph.
    @Column(name = "id_country", insertable = false, updatable = false)
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
