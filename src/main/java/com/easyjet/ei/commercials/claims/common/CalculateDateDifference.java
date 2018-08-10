package com.easyjet.ei.commercials.claims.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class CalculateDateDifference {
	
	private static Logger logger = Logger.getLogger(CalculateDateDifference.class);

	public static long getDateDifference(String date1_string, String date2_string, String resultRequired) {
		
		

		long date_difference = 0;
		
			try {
				Date date1;
				Date date2;

				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

				date1 = format.parse(date1_string);
				date2 = format.parse(date2_string);

				long diff = date1.getTime() - date2.getTime();
				
				if ("Minutes".equals(resultRequired)) {
					date_difference = TimeUnit.MILLISECONDS.toMinutes(diff);
				} else if ("Hours".equals(resultRequired)) {
					date_difference = TimeUnit.MILLISECONDS.toHours(diff);
				} else if ("Days".equals(resultRequired)) {
					date_difference = TimeUnit.MILLISECONDS.toDays(diff);
				}
			} catch (ParseException e) {				
				logger.error(e);
				
			}catch (NullPointerException n) {

				logger.error(n);

			}
		
		return date_difference;

	}
	
	public static void main(String args[]){

	}

}
