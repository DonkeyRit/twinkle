package com.github.donkeyrit.twinkle.dal.models;

import com.github.donkeyrit.twinkle.dal.interfaces.BaseDbModel;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "clients")
public class Client extends BaseDbModel {

	//#region Fields

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_client")
	private int id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "second_name")
	private String secondName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "address")
	private String address;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "id_user")
	private int userId;

	//#endregion
	//#region Getters/Setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	//#endregion
}
