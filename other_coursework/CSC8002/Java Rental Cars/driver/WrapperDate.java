package uk.ac.ncl.CSC8002.b2052238.driver;

import java.util.Calendar;
import java.util.Date;

/**
 * WrapperDate Class is created to reduce text within JUnit testing. Dates are
 * passed here to create object instances for birthdays and Issue dates
 * 
 * @author Gary Carr
 * 
 */
public class WrapperDate {
	private final Date date;

	/**
	 * Constructor creates a Calendar instance. This is then set to the date
	 * passed into the parameters
	 * 
	 * @param day
	 *            The day of the month of the event
	 * @param month
	 *            The month of the year of the event. This is then minused by 1
	 *            to set to correct month as a user would type
	 * @param year
	 *            The year of the event
	 */
	public WrapperDate(int day, int month, int year) {
		final Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day);
		this.date = calendar.getTime();
	}

	/**
	 * Method copies the date and returns it as a Date
	 * 
	 * @return Returns a copy of the date of the event
	 */
	public Date getDate() {
		return (Date) (date.clone());
	}

}
