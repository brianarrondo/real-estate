package com.realstate.utils;

import java.util.Calendar;
import java.util.Date;

public class Utils {
	public static boolean sameMonthAndYear(Date date1, Date date2) {
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar1.setTime(date1);
		calendar2.setTime(date2);
		return (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH));
	}
}
