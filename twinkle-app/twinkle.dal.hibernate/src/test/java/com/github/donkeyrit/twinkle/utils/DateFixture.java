package com.github.donkeyrit.twinkle.utils;

import java.sql.Date;
import java.util.Calendar;

public class DateFixture {

	public static Date getDate(int year, int month, int day) {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		Date date = new Date(cal.getTimeInMillis());
		return date;
	}
}
