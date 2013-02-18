package de.weidengraben.mfa.logic;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateHelper {

	public static GregorianCalendar getWeekToShow() {
		GregorianCalendar now = new GregorianCalendar();
		int day = now.get(GregorianCalendar.DAY_OF_WEEK);
		if (day == GregorianCalendar.SUNDAY) {
			now.add(GregorianCalendar.DATE, 1);
		} else if (day == GregorianCalendar.SATURDAY) {
			now.add(GregorianCalendar.DATE, 2);
		} else if (day == GregorianCalendar.MONDAY) {
		} else if (day == GregorianCalendar.TUESDAY) {
			now.add(GregorianCalendar.DATE, -1);
		} else if (day == GregorianCalendar.WEDNESDAY) {
			now.add(GregorianCalendar.DATE, -2);
		} else if (day == GregorianCalendar.THURSDAY) {
			now.add(GregorianCalendar.DATE, -3);
		} else if (day == GregorianCalendar.FRIDAY) {
			now.add(GregorianCalendar.DATE, -4);
		}
		return now;
	}

	public static String[] getWeekDaysToShow() {
		String[] out = new String[5];
		GregorianCalendar start = getWeekToShow();
		for (int i = 0; i < 5; i++){
			out[i] = "" +start.get(GregorianCalendar.YEAR) + adjLen((start.get(GregorianCalendar.MONTH) + 1), 2) +	adjLen(start.get(GregorianCalendar.DAY_OF_MONTH), 2);
			start.add(GregorianCalendar.DATE, 1);
		}
		return out;		
	}
	
	public static String adjLen(int val, int len) {
		StringBuffer out = new StringBuffer(Integer.toString(val));
		while (out.length() < len) {
			out.insert(0, "0");
		}
		return out.toString();
	}
	
	public static int getWeekNumber(GregorianCalendar date) {
		return date.get(GregorianCalendar.WEEK_OF_YEAR);
	}
	
	public static String getWeekNumber(GregorianCalendar date, int length) {
		String s = Integer.toString(getWeekNumber(date));
		
		while (s.length() < length) s = "0" + s;
		return s;
	}
	
	
	public static int getTodayIndex() {
		GregorianCalendar date = new GregorianCalendar();
		int tmp = date.get(Calendar.DAY_OF_WEEK);
		if (tmp == GregorianCalendar.SATURDAY || tmp == GregorianCalendar.SUNDAY) { // Diese Methode ändert am WE das Datum auf den kommenden Montag um den neuen Plan zu bekommen.
			int toAdd = (tmp == GregorianCalendar.SUNDAY) ? 1 : 2;
			date.add(Calendar.DATE, toAdd);
		}
		switch (tmp) {
			case GregorianCalendar.MONDAY:
				tmp = 0;
				break;
			case GregorianCalendar.TUESDAY:
				tmp = 1;
				break;
			case GregorianCalendar.WEDNESDAY:
				tmp = 2;
				break;
			case GregorianCalendar.THURSDAY:
				tmp = 3;
				break;
			case GregorianCalendar.FRIDAY:
				tmp = 4;
				break;
			case GregorianCalendar.SATURDAY:
				tmp = 0;
				break;
			case GregorianCalendar.SUNDAY:
				tmp = 0;
				break;
		}
		return tmp;
	}

}
