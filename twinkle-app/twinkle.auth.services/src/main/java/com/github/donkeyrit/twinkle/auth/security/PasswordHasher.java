package com.github.donkeyrit.twinkle.auth.security;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Salted, adaptive password hashing backed by BCrypt.
 *
 * Replaces the previous unsalted SHA-1 HashManager: SHA-1 is fast and
 * deterministic, so two users with the same password get the same hash and
 * the whole password table can be attacked with a rainbow table. BCrypt
 * generates a random salt per call and is deliberately slow, which is what
 * you want for password storage.
 */
public class PasswordHasher {

	private static final int WORK_FACTOR = 12;

	private PasswordHasher() {
	}

	/**
	 * Hashes a plaintext password for storage. Never returns the same value
	 * twice for the same input, since a fresh random salt is generated each
	 * call.
	 */
	public static String hash(String plainPassword) {
		if (plainPassword == null) {
			throw new IllegalArgumentException("Password must not be null.");
		}

		return BCrypt.hashpw(plainPassword, BCrypt.gensalt(WORK_FACTOR));
	}

	/**
	 * Verifies a plaintext password against a previously stored hash.
	 */
	public static boolean verify(String plainPassword, String storedHash) {
		if (plainPassword == null || storedHash == null || storedHash.isEmpty()) {
			return false;
		}

		try {
			return BCrypt.checkpw(plainPassword, storedHash);
		} catch (IllegalArgumentException ex) {
			// storedHash isn't a valid BCrypt hash (e.g. leftover legacy data).
			return false;
		}
	}
}
