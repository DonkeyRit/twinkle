package com.github.donkeyrit.twinkle.utils;

import java.time.LocalDate;

public class DateFixture {

	public static LocalDate getDate(int year, int month, int day) {
		// Preserves the historical (buggy-looking but established) 0-based month callers use,
		// e.g. getDate(2020, 0, 1) means January 2020.
		return LocalDate.of(year, month + 1, day);
	}
}
