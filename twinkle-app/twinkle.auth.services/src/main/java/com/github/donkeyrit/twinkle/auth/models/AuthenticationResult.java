package com.github.donkeyrit.twinkle.auth.models;

import com.github.donkeyrit.twinkle.dal.models.User;
import java.util.Optional;

public record AuthenticationResult(boolean isSuccessfull, String errorMessage, Optional<User> authenticatedUser) {

	public static AuthenticationResult error(String message){
		return new AuthenticationResult(false, message, Optional.empty());
	}

	public static AuthenticationResult fromResult(Optional<User> user){

		if(user.isPresent())
		{
			return new AuthenticationResult(true, null, user);
		}

		return new AuthenticationResult(false, "Incorrect login or password.", Optional.empty());
	}
}
