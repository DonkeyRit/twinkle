package com.github.donkeyrit.twinkle.auth.security;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory brute-force guard for login attempts, keyed by username.
 *
 * After {@value #MAX_FAILED_ATTEMPTS} consecutive failures for a username,
 * further attempts are rejected for a cooldown window without touching the
 * database or running password verification. State is per-process and reset
 * on restart, which is fine for this app's single-instance deployment model.
 */
public class LoginAttemptLimiter {

	private static final int MAX_FAILED_ATTEMPTS = 5;
	private static final Duration LOCKOUT_DURATION = Duration.ofMinutes(1);

	private final Clock clock;
	private final ConcurrentHashMap<String, State> attemptsByUsername = new ConcurrentHashMap<>();

	public LoginAttemptLimiter() {
		this(Clock.systemUTC());
	}

	LoginAttemptLimiter(Clock clock) {
		this.clock = clock;
	}

	public boolean isLockedOut(String username) {
		State state = attemptsByUsername.get(username);
		return state != null && state.lockedUntil != null && clock.instant().isBefore(state.lockedUntil);
	}

	public void recordFailure(String username) {
		Instant now = clock.instant();
		attemptsByUsername.compute(username, (key, existing) -> {
			State state = (existing == null || existing.isStale(now)) ? new State() : existing;
			state.failedAttempts++;
			if (state.failedAttempts >= MAX_FAILED_ATTEMPTS) {
				state.lockedUntil = now.plus(LOCKOUT_DURATION);
			}
			return state;
		});
	}

	public void recordSuccess(String username) {
		attemptsByUsername.remove(username);
	}

	private static class State {
		int failedAttempts;
		Instant lockedUntil;

		boolean isStale(Instant now) {
			return lockedUntil != null && !now.isBefore(lockedUntil);
		}
	}
}
