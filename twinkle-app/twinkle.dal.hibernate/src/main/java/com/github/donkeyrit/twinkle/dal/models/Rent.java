package com.github.donkeyrit.twinkle.dal.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Date;

import com.github.donkeyrit.twinkle.dal.interfaces.BaseDbModel;

@Entity
@Table(name = "rent")
public class Rent extends BaseDbModel {

	//#region Fields

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_rent")
	private int id;

	@Column(name = "id_client")
	private int idClient;

	@Column(name = "id_car")
	private int idCar;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "plan_date")
	private Date planDate;

	@Column(name = "end_date")
	private Date endDate;

	//#endregion
	//#region Getters/Setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdClient() {
		return idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	public int getIdCar() {
		return idCar;
	}

	public void setIdCar(int idCar) {
		this.idCar = idCar;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getPlanDate() {
		return planDate;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	//#endregion

}
