package uk.ac.ncl.CSC8002.b2052238.car;

import java.util.HashMap;
import java.util.Map;

/**
 * RegFactory - Creates a guaranteed unique registration plate for the
 * AbstractCar class. A static Map is used to hold all the registration plates
 * created to make sure 2 of the same type are never created.
 * 
 * @author Gary Carr
 */
public final class RegFactory {

	private static String LETTER = "A";
	private static int NUMBER = 0;
	private final String carLetter;
	private final String carNumber;
	private final String strRep;
	private static final Map<String, RegFactory> REGLIST = new HashMap<String, RegFactory>();
	private static int COUNT;

	/**
	 * Constructs a new registration plate and increments the number by one to
	 * guarantee a unique instance
	 * 
	 * @param strRep
	 *            Variable is passed by the getInstance method which has already
	 *            concatenated the unique registration plate to a string
	 */
	private RegFactory(String strRep) {
		this.carNumber = strRep.substring(1, 5);
		this.carLetter = LETTER;
		this.strRep = strRep;
		incrementNumber();
	}

	/**
	 * The static method checks the static int count is under 25. It has
	 * exceeded 25 then all available numbers and letters (A-Z) have been used
	 * and an exception is thrown.
	 * 
	 * A string representation of the license plate is created from the static
	 * number and letter fields and checked against the HashMap to see if it is
	 * a duplicate. If it is a duplicate an exception is thrown, otherwise the
	 * plate is created and returned and added to the HashMap
	 * 
	 * @return Returns the new registration plate
	 * @throws IllegalArgumentException
	 *             if a duplicated registration plate is created
	 */
	public static RegFactory getInstance() {
		if (COUNT > 25)
			throw new IllegalArgumentException(
					"Full range of unique plates have been used");

		final String strRep = LETTER + String.format("%04d", NUMBER);
		RegFactory r = REGLIST.get(strRep);

		if (r != null)
			throw new IllegalArgumentException(
					"Unexpected duplicate registration plate created.");

		r = new RegFactory(strRep);
		REGLIST.put(strRep, r);
		return r;

	}

	/**
	 * Method increases the static number in the class by 1. When number reaches
	 * 10000 it is reset to 0 and the letter is incremented to ensure uniqueness
	 */
	private void incrementNumber() {

		if (NUMBER >= 9999) {
			NUMBER = 0;
			incrementLetter();
			return;
		}
		NUMBER++;
	}

	/**
	 * Method increases the static letter up the alphabet. The static int count
	 * is added, when it reaches 25 the letter will have reached Z, at which
	 * point no more plates can be created
	 */
	private void incrementLetter() {
		int charValue = LETTER.charAt(0);
		LETTER = String.valueOf((char) (charValue + 1));
		COUNT++;
	}

	/**
	 * @return Returns the letter of the registration plate
	 */
	public String getLetter() {
		return carLetter;
	}

	/**
	 * @return Returns the number of the car registration (as a string to
	 *         include where zero's precede the number)
	 */
	public String getNumber() {
		return carNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return strRep;
	}

	/**
	 * Method for JUnit testing, to reset lists and static variables
	 */
	static void resetTesting() {
		NUMBER = 0;
		LETTER = "A";
		REGLIST.clear();
		COUNT = 0;
	}

	/**
	 * Method for JUnit testing, to reset lists and static variables
	 */
	static void resetTestingDuplicates() {
		NUMBER = 0;
		LETTER = "A";
		COUNT = 0;
	}

}