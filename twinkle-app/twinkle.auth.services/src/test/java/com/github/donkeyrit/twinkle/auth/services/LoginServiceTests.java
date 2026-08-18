package com.github.donkeyrit.twinkle.auth.services;

import com.github.donkeyrit.twinkle.auth.models.AuthenticationResult;
import com.github.donkeyrit.twinkle.dal.models.User;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginServiceTests {

	private InMemoryUserRepository userRepository;
	private DefaultLoginService loginService;

	@BeforeMethod
	public void setUp() {
		userRepository = new InMemoryUserRepository();
		loginService = new DefaultLoginService(userRepository);
	}

	@Test
	public void signUp_createsUserWithHashedPassword() {
		AuthenticationResult result = loginService.signUp("new_user", "correct-password", "correct-password");

		assertThat(result.isSuccessfull()).isTrue();
		User createdUser = result.authenticatedUser().orElseThrow();
		assertThat(createdUser.getLogin()).isEqualTo("new_user");
		assertThat(createdUser.getPassword()).isNotEqualTo("correct-password");
	}

	@Test
	public void signUp_rejectsMismatchedConfirmation() {
		AuthenticationResult result = loginService.signUp("new_user", "correct-password", "different-password");

		assertThat(result.isSuccessfull()).isFalse();
		assertThat(result.errorMessage()).isEqualTo("Passwords do not match.");
	}

	@Test
	public void signUp_rejectsExistingLogin() {
		loginService.signUp("existing_user", "correct-password", "correct-password");

		AuthenticationResult result = loginService.signUp("existing_user", "another-password", "another-password");

		assertThat(result.isSuccessfull()).isFalse();
		assertThat(result.errorMessage()).isEqualTo("Login already exist");
	}

	@Test
	public void signUp_rejectsShortPassword() {
		AuthenticationResult result = loginService.signUp("new_user", "short1", "short1");

		assertThat(result.isSuccessfull()).isFalse();
	}

	@Test
	public void signUp_rejectsShortLogin() {
		AuthenticationResult result = loginService.signUp("ab", "correct-password", "correct-password");

		assertThat(result.isSuccessfull()).isFalse();
	}

	@Test
	public void signUp_reportsErrorWhenPersistenceFails() {
		userRepository.failNextSave();

		AuthenticationResult result = loginService.signUp("new_user", "correct-password", "correct-password");

		assertThat(result.isSuccessfull()).isFalse();
	}

	@Test
	public void verifyCredentials_succeedsWithCorrectPassword() {
		loginService.signUp("existing_user", "correct-password", "correct-password");

		AuthenticationResult result = loginService.verifyCredentials("existing_user", "correct-password");

		assertThat(result.isSuccessfull()).isTrue();
		assertThat(result.authenticatedUser().orElseThrow().getLogin()).isEqualTo("existing_user");
	}

	@Test
	public void verifyCredentials_failsWithWrongPassword() {
		loginService.signUp("existing_user", "correct-password", "correct-password");

		AuthenticationResult result = loginService.verifyCredentials("existing_user", "wrong-password");

		assertThat(result.isSuccessfull()).isFalse();
		assertThat(result.errorMessage()).isEqualTo("Incorrect login or password.");
	}

	@Test
	public void verifyCredentials_failsForUnknownUser() {
		AuthenticationResult result = loginService.verifyCredentials("nobody", "correct-password");

		assertThat(result.isSuccessfull()).isFalse();
		assertThat(result.errorMessage()).isEqualTo("Incorrect login or password.");
	}

	@Test
	public void verifyCredentials_locksOutAfterRepeatedFailures() {
		loginService.signUp("existing_user", "correct-password", "correct-password");

		for (int i = 0; i < 5; i++) {
			loginService.verifyCredentials("existing_user", "wrong-password");
		}

		AuthenticationResult result = loginService.verifyCredentials("existing_user", "correct-password");

		assertThat(result.isSuccessfull()).isFalse();
		assertThat(result.errorMessage()).isEqualTo("Too many failed attempts. Please try again in a minute.");
	}
}
