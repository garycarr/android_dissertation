package uk.ac.ncl.CSC8002.b2052238.driver;

/**
 * NameFactory - Factory class for creating name objects consisting of Strings
 * of first and last name.
 * 
 * @author Gary Carr
 */
public final class NameFactory {
	private final String fName;
	private final String lName;

	/**
	 * Constructs a new person object with first and last name. Checks on the
	 * validity of the Strings are carried out in getInstance()
	 * 
	 * @param fName
	 *            String of persons first name
	 * @param lName
	 *            String of persons second name
	 */
	private NameFactory(String fName, String lName) {
		this.fName = fName;
		this.lName = lName;
	}

	/**
	 * The static method checks that the Strings entered are not null or blank
	 * and then passes it to the constructor to create the object, which is then
	 * return. If the Strings are invalid and exception is thrown
	 * 
	 * @param fName
	 *            String of persons first name. Cannot be blank or null
	 * @param lName
	 *            String of persons last name. Cannot be blank or null
	 * @return Returns a new NameFactory object
	 * @throws IllegalArgumentException
	 *             if the name is blank or null
	 */
	public static NameFactory getInstance(String fName, String lName) {
		if (fName.length() == 0)
			throw new IllegalArgumentException(
					"Null or blank first name entered");
		if (lName.length() == 0)
			throw new IllegalArgumentException(
					"Null or blank second name entered");

		final NameFactory n = new NameFactory(fName, lName);
		return n;
	}

	/**
	 * @return Method returns the first name
	 */
	public String getFName() {
		return fName;
	}

	/**
	 * @return Method returns the last name
	 */
	public String getLName() {
		return lName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return fName + " " + lName;
	}

	/**
	 * @param nameFactory
	 *            String of persons name seperated by a space. Name can only
	 *            contain 2 parts (e.g. "Gary Carr"
	 * @return Method creates a new name from a string.
	 * @throws IllegalArgumentException
	 *             thrown if name has been inputted incorrectly
	 * @throws ArrayIndexOutOfBoundsException
	 *             If more or less than 2 Strings for name are entered
	 * @throws NullPointerException
	 *             If Strings are null
	 */
	public static NameFactory valueOf(String nameFactory) {
		final String[] parts = nameFactory.split(" ");
		if (parts.length > 2)
			throw new ArrayIndexOutOfBoundsException(
					"More than 2 names entered");
		final String fName = parts[0].trim();
		final String lName = parts[1].trim();
		final NameFactory n = getInstance(fName, lName);
		return n;
	}

}
