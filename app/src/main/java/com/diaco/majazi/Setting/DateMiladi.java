package com.diaco.majazi.Setting;

import java.util.Arrays;
import java.util.Date;


public class DateMiladi
{
	/*----- Define Variable ---*/
	private Long timeInMilliSecond;
	private int shYear;


	public DateMiladi() {
		this.timeInMilliSecond = new Date().getTime();
	}

	private int[][] grgSumOfDays = {{0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365}, {0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366}};
	private int[][] hshSumOfDays = {{0, 31, 62, 93, 124, 155, 186, 216, 246, 276, 306, 336, 365}, {0, 31, 62, 93, 124, 155, 186, 216, 246, 276, 306, 336, 366}};



	public Long getTime() {
		return this.timeInMilliSecond;
	}


	public boolean grgIsLeap(int Year) {
		return ((Year % 4) == 0 && ((Year % 100) != 0 || (Year % 400) == 0));
	}


	public boolean isLeap() {
		return this.isLeap(this.shYear);
	}
	boolean isLeap(int year) {
		double referenceYear = 1375;
		double startYear = 1375;
		double yearRes = year - referenceYear;
		if(yearRes > 0){
			if(yearRes >= 33){
				double numb = yearRes / 33;
				startYear = referenceYear + Math.floor(numb)*33;
			}
		}else{
			if(yearRes >= -33){
				startYear = referenceYear-33;
			}else{
				double numb = Math.abs(yearRes / 33);
				startYear = referenceYear - (Math.floor(numb)+1)*33;
			}
		}
		double[] leapYears = {startYear,startYear+4,startYear+8,startYear+16,startYear+20,startYear+24,startYear+28,startYear+33};
		return (Arrays.binarySearch(leapYears,year)) >= 0;
	}

	public int[] toGregorian(int year, int month, int day) {
		int grgYear = year + 621;
		int grgDay = 0;
		int grgMonth = 0;
		int grgElapsed;

		boolean hshLeap = this.isLeap(year);
		boolean grgLeap = this.grgIsLeap(grgYear);

		int hshElapsed = hshSumOfDays[hshLeap ? 1 : 0][month - 1] + day;

		if(month > 10 || (month == 10 && hshElapsed > 286 + (grgLeap ? 1 : 0))){
			grgElapsed = hshElapsed - (286 + (grgLeap ? 1 : 0));
			grgLeap = grgIsLeap(++grgYear);
		}else{
			hshLeap = this.isLeap(year - 1);
			grgElapsed = hshElapsed + 79 + (hshLeap ? 1 : 0) - (grgIsLeap(grgYear - 1) ? 1 : 0);
		}
		if(grgYear >= 2030 && (grgYear-2030)%4 == 0){
			grgElapsed--;
		}
		if(grgYear == 1989){
			grgElapsed++;
		}
		for(int i = 1; i <= 12; i++){
			if(grgSumOfDays[grgLeap ? 1 : 0][i] >= grgElapsed){
				grgMonth = i;
				grgDay = grgElapsed - grgSumOfDays[grgLeap ? 1 : 0][i - 1];
				break;
			}
		}

		Days = grgDay ;
		return new int[]{grgYear, grgMonth, grgDay};
	}

	static int Days ;
	public static void setDays (int days) {
		Days = days ;
	}

	public int printDifference(Date startDate, Date endDate) {
		//milliseconds
		/*long different = endDate.getTime() - startDate.getTime();
		long secondsInMilli = 1000;
		long minutesInMilli = secondsInMilli * 60;
		long hoursInMilli = minutesInMilli * 60;
		long daysInMilli = hoursInMilli * 24;

		long elapsedDays = different / daysInMilli;*/
		long diff = startDate.getTime() - endDate.getTime();
		if (diff < 0) {
			diff = diff * (-1);
		}
		return Math.round(diff/86400000f) ;
	}

	public long printDifferenceFormatMilli(Date startDate, Date endDate) {
		return endDate.getTime() - startDate.getTime();
	}
	public long printDifferenceFormatMilliAbs(Date startDate, Date endDate) {
		long myDate = endDate.getTime() - startDate.getTime();
		myDate = myDate < 0 ? myDate * -1 : myDate ;
		return myDate;
	}

	public static String getDay (int day) {
		switch (day) {
			case 1 : return "ی";
			case 2 : return "د";
			case 3 : return "س";
			case 4 : return "چ";
			case 5 : return "پ";
			case 6 : return "ج";
			case 7 : return "ش";
			default:return "";
		}
	}
}