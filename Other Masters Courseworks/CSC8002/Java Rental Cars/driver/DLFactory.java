package uk.ac.ncl.CSC8002.b2052238.driver;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * DLFactory - Creates a guaranteed unique driving license for the
 * DriversLicense class with fields from Name and Date objects. A static Map is
 * used to hold all the drivers licenses created to make sure 2 of the same type
 * are never created
 * 
 * @author Gary Carr
 */
public final class DLFactory {
	private static final Map<String, String> DLIST = new HashMap<String, String>();
	private static int NUMBER = 1;
	private final String dLicense;
	private final String strRep;

	/**
	 * Constructs a new driving license and increments the number by one to
	 * guarantee a unique instance
	 * 
	 * @param name
	 *            Name object for the driver
	 * @param issueDate
	 *            Date of Issue of the drivers license
	 * @param strRep
	 *            Variable is passed by the getInstance method which has already
	 *            concatenated the unique drivers license to a string
	 */
	private DLFactory(String dLicense, String strRep) {
		this.dLicense = dLicense;
		this.strRep = strRep;
		incrementNumber();
	}

	/**
	 * The static method takes a NameFactory object and Date of license issue
	 * and concatenates them to a string with a static number to ensure
	 * uniqueness. This string is then checked against the HashMap, if a
	 * duplicate is found an exception is thrown, otherwise the map is updated
	 * with the new drivers license, and a new license created and then returned
	 * 
	 * @param name
	 *            Name object for the driver
	 * @param issDate
	 *            Date of Issue of the drivers license
	 * @return Returns a new driving license.
	 * @throws IllegalArgumentException
	 *             if another instance of the same drivers license is found then
	 *             an exception is thrown
	 * @throws IllegalArgumentException
	 *             if a future date has been entered
	 */
	public static DLFactory getInstance(NameFactory name, Date issDate) {
		final Date d = new Date(issDate.getTime());

		if (d.after(new Date()))
			throw new IllegalArgumentException("Date of issue is in the future");
		final Calendar iD = Calendar.getInstance();
		iD.setTime(d);
		final int iYear = iD.get(Calendar.YEAR);

		String dLicense = name.getFName().charAt(0) + ""
				+ name.getLName().charAt(0) + "-" + iYear + "-"
				+ String.format("%05d", NUMBER);

		final String checkUnique = DLIST.get(dLicense);
		if (checkUnique != null)
			throw new IllegalArgumentException(
					"Unexpected duplicate license number.");

		final SimpleDateFormat display = new SimpleDateFormat("d/M/yyyy");
		final String strRep = name + " - " + display.format(issDate);

		final DLFactory dL = new DLFactory(dLicense, strRep);
		DLIST.put(dLicense, strRep);
		return dL;
	}

	/**
	 * Method increases the static number by one
	 */
	private void incrementNumber() {
		NUMBER++;
	}

	/**
	 * @return Returns the unique driving license number
	 */
	public String getDriversLicense() {
		return dLicense;
	}

	/*
	 * Returns the full name and Date of Issue of the the driver
	 * 
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return strRep;
	}

	/**
	 * Method exists only for testing. Will clear any entry in the HashMap and
	 * reset the static number
	 */
	static void resetTesting() {
		NUMBER = 1;
		DLIST.clear();

	}

	/**
	 * Method exists only for testing exceptions thrown for duplicates. Reset
	 * the static number
	 */
	static void resetTestingDuplicates() {
		NUMBER = 1;
	}

	/**
	 * Method takes a String and with the text creates a driving license. The
	 * text must be entered 'FirstName SecondName - D/M/YYYY' or for example
	 * "Gary Carr - 1/1/1990", where the date is the date the license was issued
	 * and then a new DLFacory object is created. If the text is entered
	 * incorrectly Exceptions are thrown.
	 * 
	 * @throws ArrayIndexOutOfBoundsException
	 *             If the string has been typed in incorrect format
	 * @throws NullPointerException
	 *             if null has been declared in the String
	 * @throws IllegalArgumentException
	 *             if a future date has been entered
	 */
	public static DLFactory valueOf(String dLFactory) {
		final String[] parts = dLFactory.split(" - ");

		if (parts.length > 2)
			throw new ArrayIndexOutOfBoundsException(
					"More than 2 names entered");

		final String[] person = dLFactory.split(" ");
		final String fName = person[0].trim();
		final String lName = person[1].trim();
		final NameFactory name = NameFactory.getInstance(fName, lName);

		final String[] iDay = parts[1].split("/");
		final int dateOfIssue = Integer.parseInt(iDay[0].trim());
		final int monthOfIssue = Integer.parseInt(iDay[1].trim());
		final int yearOfIssue = Integer.parseInt(iDay[2].trim());

		final Calendar c = Calendar.getInstance();
		c.set(yearOfIssue, monthOfIssue - 1, dateOfIssue);
		final Date issueDate = c.getTime();

		return getInstance(name, issueDate);
	}
}
