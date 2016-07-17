package uk.ac.ncl.CSC8002.b2052238.driver;

import java.util.Date;

/**
 * DriversLicense - Class holds information on the customer and passes to the
 * DLFactory to create a unique license number. The class is concerned with
 * holding the Name, dates of birth, age of license, and if the driver has a
 * full license.
 * 
 * @author Gary Carr
 */
public final class DriversLicense {

	private final Date dob;
	private final Date issueDate;
	private final DLFactory licenseNumber;
	private final NameFactory name;
	private final boolean isFull;

	/**
	 * Constructor creates a driving license object. The user must input a name,
	 * date of birth, date of issue, and boolean specifying if the license held
	 * is full. The name and issue date are passed to DLFactory to create a new
	 * unique license number
	 * 
	 * @param name
	 *            NameFactory object for the drivers name
	 * @param dob
	 *            Date of birth of the driver
	 * @param issueDate
	 *            Date the driver was issued their license
	 * @param isFull
	 *            Boolean specifying true if driver holds a full license
	 * @throws IllegalArgumentException
	 *             if a future date has been entered
	 */
	public DriversLicense(NameFactory name, Date dob, Date issueDate,
			boolean isFull) {
		this.dob = new Date(dob.getTime());
		this.issueDate = new Date(issueDate.getTime());

		if (this.dob.after(new Date()))
			throw new IllegalArgumentException("Date of birth in the future");
		if (this.issueDate.after(new Date()))
			throw new IllegalArgumentException("Date of issue is in the future");

		this.name = name;
		this.isFull = isFull;
		this.licenseNumber = DLFactory.getInstance(this.name, this.issueDate);
	}

	/**
	 * Method returns the name held for the person
	 * 
	 * @return Returns the object Name
	 */
	public NameFactory getName() {
		return name;
	}

	/**
	 * Method returns true if the driver holds a full license
	 * 
	 * @return Returns true if the driving license is a full license
	 */
	public boolean hasFullLicense() {
		return isFull;
	}

	/**
	 * Method returns the date of birth of the driver
	 * 
	 * @return Returns a copy of the date of birth for the driver
	 */
	public Date getDateOfBirth() {
		return (Date) (dob.clone());
	}

	/**
	 * Method returns the date of issue of the driver
	 * 
	 * @return Returns a copy of the date of issue for the driving license
	 */
	public Date getDateOfIssue() {
		return (Date) (issueDate.clone());
	}

	/**
	 * Method returns a string version of the license number
	 * 
	 * @return Returns a String version of the license number
	 */
	public String getLicenseNumber() {
		return licenseNumber.getDriversLicense();
	}

	/*
	 * Note - toString method could be changed at a future date
	 * 
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return name + " - " + licenseNumber.getDriversLicense();
	}
}
