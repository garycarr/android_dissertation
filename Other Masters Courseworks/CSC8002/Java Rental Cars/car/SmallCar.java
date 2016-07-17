package uk.ac.ncl.CSC8002.b2052238.car;

/**
 * SmallCar - creates a small car object with a fixed capacity in the super
 * class. Methods are defined in interface, for method definitions see
 * uk.ac.ncl.CSC8002.b2052238.rentalCarCoursework.InterfaceAbstractCar
 * 
 * @author Gary Carr
 */
final class SmallCar extends AbstractCar {

	private final static int SMALLCONSUMPTION = 20;
	private final static int CAPACITY = 49;
	private final static int DRIVINGAGE = 20;
	private final static int LICENSEAGE = 1;
	private final static TypeOfCar typeOfCar = TypeOfCar.SMALL;

	/**
	 * Constructor creates a Small Car with a static capacity in the super class
	 */
	SmallCar() {
		super(CAPACITY);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.ncl.CSC8002.b2052238.rentalCarCoursework.InterfaceAbstractCar#drive
	 * (int)
	 */
	public int drive(int distance) {
		if (!checkIfCanDrive(distance))
			return 0;

		int totalConsumption = (distance / SMALLCONSUMPTION);
		// Below if statement determines if distance/SMALLCONSUMPTION returned a
		// whole number. If not a remainder was present, so 1 is added
		if ((totalConsumption * SMALLCONSUMPTION) != distance)
			totalConsumption++;
		amountOfFuelUsed(totalConsumption);
		return totalConsumption;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ncl.CSC8002.b2052238.rentalCarCoursework.InterfaceAbstractCar#
	 * getDrivingAge()
	 */
	public int getDrivingAge() {
		return DRIVINGAGE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ncl.CSC8002.b2052238.rentalCarCoursework.InterfaceAbstractCar#
	 * getLicenseAge()
	 */
	public int getLicenseAge() {
		return LICENSEAGE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.ncl.CSC8002.b2052238.rentalCarCoursework.InterfaceAbstractCar#getCar
	 * ()
	 */
	public InterfaceAbstractCar getCar() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.ncl.CSC8002.b2052238.rentalCarCoursework.AbstractCar#toString()
	 */
	public String toString() {
		return super.toString() + " - Small Car";
	}

	/* (non-Javadoc)
	 * @see uk.ac.ncl.CSC8002.b2052238.rentalCarCoursework.InterfaceAbstractCar#getType()
	 */
	public TypeOfCar getType() {
		return typeOfCar;
	}
}