package com.github.donkeyrit.twinkle.dal.models;

public record Client(
	int id,
	String firstName,
	String secondName,
	String middleName,
	String address,
	String phoneNumber,
	int userId
) 
{}
