package com.github.donkeyrit.twinkle.dal.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.github.donkeyrit.twinkle.dal.interfaces.BaseDbModel;

@Entity
@Table(name = "resulting_injury")
public class ResultingInjury1 extends BaseDbModel {

	//#region Fields

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "id_rent")
	private int idRent;

	@Column(name = "id_injury")
	private int idInjury;

	//#endregion
	//#region Getters/Setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdRent() {
		return idRent;
	}

	public void setIdRent(int idRent) {
		this.idRent = idRent;
	}

	public int getIdInjury() {
		return idInjury;
	}

	public void setIdInjury(int idInjury) {
		this.idInjury = idInjury;
	}

	//#endregion
}
