package com.github.donkeyrit.twinkle.dal.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "mark")
public class MarkOfCar {

	// #region Fields

	@Id
	@Column(name = "id_mark")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "mark_name")
	private String name;

	@OneToOne()
	@JoinColumn(unique = true, name = "id_country")
	private Country country;

	// #endregion
	// #region Getters/Setters

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

	// #endregion

	@Override
	public String toString() {
		return name;
	}
}
