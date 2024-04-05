package com.github.donkeyrit.twinkle.dal.specifications;

import com.github.donkeyrit.twinkle.dal.common.specifications.QuerySpecification;
import com.github.donkeyrit.twinkle.dal.models.User;

public abstract class UserInfoSpecifciation<TPredicate> implements QuerySpecification<User> {
	
	//#region Fields
	private String login;
	private String passwordHash;
	//#endregion

	//#region Getters/Setters

	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	//#endregion

	//#region Constructors

	public UserInfoSpecifciation(String login, String passwordHash) {
		this.login = login;
		this.passwordHash = passwordHash;
	}
	public UserInfoSpecifciation(String login) {
		this.login = login;
	}

	////#endregion

	public abstract TPredicate toCondition();
}
