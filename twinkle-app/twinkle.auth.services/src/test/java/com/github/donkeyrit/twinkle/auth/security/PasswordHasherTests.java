package com.github.donkeyrit.twinkle.auth.security;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordHasherTests {

	@Test
	public void hash_producesDifferentHashesForTheSamePassword() {
		String first = PasswordHasher.hash("correct-password");
		String second = PasswordHasher.hash("correct-password");

		assertThat(first).isNotEqualTo(second);
	}

	@Test
	public void verify_succeedsForTheOriginalPassword() {
		String hash = PasswordHasher.hash("correct-password");

		assertThat(PasswordHasher.verify("correct-password", hash)).isTrue();
	}

	@Test
	public void verify_failsForAWrongPassword() {
		String hash = PasswordHasher.hash("correct-password");

		assertThat(PasswordHasher.verify("wrong-password", hash)).isFalse();
	}

	@Test
	public void verify_failsForALegacyOrMalformedHash() {
		assertThat(PasswordHasher.verify("correct-password", "not-a-bcrypt-hash")).isFalse();
	}
}
