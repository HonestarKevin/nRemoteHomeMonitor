package com.zerochip.util;

import java.util.Calendar;

public class DateUtil {

	public int Year = 0;
	public int MonthOfYear = 0;
	public int DayOfMonth = 0;
	
	public Calendar mCalendar = null;
	private static DateUtil mDateUtil = null;
	
	private DateUtil() {
		super();
		mCalendar = Calendar.getInstance();
		Year = mCalendar.get(Calendar.YEAR);
		MonthOfYear = mCalendar.get(Calendar.MONTH);
		DayOfMonth =  mCalendar.get(Calendar.DAY_OF_MONTH);
	}
	public static DateUtil getInstance()
	{
		if(mDateUtil == null)
			mDateUtil = new DateUtil();
		return mDateUtil;
	}
	public int getYear() {
		return Year;
	}
	public int getMonthOfYear() {
		return MonthOfYear;
	}
	public int getDayOfMonth() {
		return DayOfMonth;
	}
}
