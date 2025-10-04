package com.github.donkeyrit.twinkle.dal.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.github.donkeyrit.twinkle.dal.interfaces.BaseDbModel;

@Entity
@Table(name = "injury")
public class Injury1 extends BaseDbModel {

	//#region Fields

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "injury_name")
	private String injuryName;

	//#endregion
	//#region Getters/Setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInjuryName() {
		return injuryName;
	}

	public void setInjuryName(String injuryName) {
		this.injuryName = injuryName;
	}

	//#endregion
}
