package com.github.donkeyrit.twinkle.auth.security;

import org.testng.annotations.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginAttemptLimiterTests {

	@Test
	public void isLockedOut_isFalseBeforeAnyFailures() {
		LoginAttemptLimiter limiter = new LoginAttemptLimiter();

		assertThat(limiter.isLockedOut("user")).isFalse();
	}

	@Test
	public void isLockedOut_isFalseBelowTheFailureThreshold() {
		LoginAttemptLimiter limiter = new LoginAttemptLimiter();

		for (int i = 0; i < 4; i++) {
			limiter.recordFailure("user");
		}

		assertThat(limiter.isLockedOut("user")).isFalse();
	}

	@Test
	public void isLockedOut_isTrueAtTheFailureThreshold() {
		LoginAttemptLimiter limiter = new LoginAttemptLimiter();

		for (int i = 0; i < 5; i++) {
			limiter.recordFailure("user");
		}

		assertThat(limiter.isLockedOut("user")).isTrue();
	}

	@Test
	public void isLockedOut_doesNotAffectOtherUsernames() {
		LoginAttemptLimiter limiter = new LoginAttemptLimiter();

		for (int i = 0; i < 5; i++) {
			limiter.recordFailure("user-a");
		}

		assertThat(limiter.isLockedOut("user-b")).isFalse();
	}

	@Test
	public void recordSuccess_clearsPriorFailures() {
		LoginAttemptLimiter limiter = new LoginAttemptLimiter();

		for (int i = 0; i < 4; i++) {
			limiter.recordFailure("user");
		}
		limiter.recordSuccess("user");
		limiter.recordFailure("user");

		assertThat(limiter.isLockedOut("user")).isFalse();
	}

	@Test
	public void isLockedOut_expiresAfterTheLockoutWindow() {
		Instant start = Instant.parse("2025-10-07T10:00:00Z");
		MutableClock clock = new MutableClock(start);
		LoginAttemptLimiter limiter = new LoginAttemptLimiter(clock);

		for (int i = 0; i < 5; i++) {
			limiter.recordFailure("user");
		}
		assertThat(limiter.isLockedOut("user")).isTrue();

		clock.advance(Duration.ofMinutes(2));

		assertThat(limiter.isLockedOut("user")).isFalse();
	}

	private static class MutableClock extends Clock {
		private Instant now;

		MutableClock(Instant now) {
			this.now = now;
		}

		void advance(Duration duration) {
			this.now = this.now.plus(duration);
		}

		@Override
		public ZoneOffset getZone() {
			return ZoneOffset.UTC;
		}

		@Override
		public Clock withZone(java.time.ZoneId zone) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Instant instant() {
			return now;
		}
	}
}
