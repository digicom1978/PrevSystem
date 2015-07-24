package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	public Utils() {}
	
	public String getTotalTimePerDay(String start, String end) {
		//HH converts hour in 24 hours format (0-23), day calculation
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
 
		Date d1 = null;
		Date d2 = null;
 
		try {
			d1 = format.parse(start);
			d2 = format.parse(end);
 
			//in milliseconds
			long diff = d2.getTime() - d1.getTime();
 
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
 
			return ( Integer.toString((int)diffHours)+":"+Integer.toString((int)diffMinutes) );
 
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public String getTotalTimePerMonth(String[] workingTimes) {
		int sum = 0;

		for( String hhmm : workingTimes ){
			String[] split = hhmm.split( ":", 2 );
			int mins = Integer.valueOf(split[ 0 ]) * 60 + Integer.valueOf( split[ 1 ] );
			sum += mins;
		}

		String formattedWorkingTime = (int)Math.floor(sum/60) + ":" + ( sum % 60 );
		return formattedWorkingTime;
	}
	
	public String getOverTimePerday(String totalWorkTime) {
		int sum = 0;
		int regularWorkTime = 480;

		String[] totals = totalWorkTime.split( ":", 2 );
		sum = Integer.valueOf(totals[ 0 ]) * 60 + Integer.valueOf( totals[ 1 ] );

		sum -= regularWorkTime;
		
		String formattedWorkingTime = (int)Math.floor(sum/60) + ":" + ( sum % 60 );
		return formattedWorkingTime;
	}

	public static void main(String[] args) {
		Utils ut = new Utils();
		String dateStart1 = "2015/05/06 09:55";
		String dateStop1 = "2015/05/06 17:45";
		System.out.println( "Total working time of the day: " + ut.getTotalTimePerDay(dateStart1, dateStop1) );
		
		String dateStart = "09:55";
		String dateStop = "17:45";
		String[] workingTimes = {dateStart, dateStop};
		System.out.println( "Sum of working time: " + ut.getTotalTimePerMonth(workingTimes) );
		
		String totalWorkTimeOfDay = "09:45";
		System.out.println( "Over time: " + ut.getOverTimePerday(totalWorkTimeOfDay) );
	}

}
