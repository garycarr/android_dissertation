package uk.ac.ncl.CSC8002.b2052238.car;

/**
 * LargeCar - creates a large car object with a fixed capacity in the super
 * class. Methods are defined in interface, for method definitions see
 * uk.ac.ncl.CSC8002.b2052238.rentalCarCoursework.InterfaceAbstractCar
 * 
 * @author Gary Carr
 */
final class LargeCar extends AbstractCar {

	private static final int LFIRSTCONSUMPTION = 10;
	private static final int LSECONDCONSUMPTION = 15;
	private static final int LFIFTYCONSUMPTION = 5;
	private static final int CAPACITY = 60;
	private static final int DRIVINGAGE = 25;
	private static final int LICENSEAGE = 5;
	private final static TypeOfCar typeOfCar = TypeOfCar.LARGE;

	/**
	 * Constructor creates a Large Car with a static capacity in the super class
	 */

	LargeCar() {
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

		if (distance < 50) {
			int totalConsumption = (distance / LFIRSTCONSUMPTION);
			// Below if statement determines if distance/SMALLCONSUMPTION
			// returned a whole number. If not a remainder was present, so 1 is
			// added
			if ((totalConsumption * LFIRSTCONSUMPTION) != distance)
				totalConsumption++;
			amountOfFuelUsed(totalConsumption);
			return totalConsumption;
		} else if (distance == 50) {
			int totalConsumption = LFIFTYCONSUMPTION;
			amountOfFuelUsed(totalConsumption);
			return totalConsumption;
		} else {
			// First fifty kilometres gives a definite answer of 5, therefore 50
			// is subtracted from the distance and 5 added in the total amount
			distance -= 50;
			int totalConsumption = LFIFTYCONSUMPTION
					+ ((distance) / LSECONDCONSUMPTION);
			if ((totalConsumption * LSECONDCONSUMPTION) != distance)
				totalConsumption++;
			amountOfFuelUsed(totalConsumption);
			return totalConsumption;
		}
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
		return super.toString() + " - Large Car";
	}

	/* (non-Javadoc)
	 * @see uk.ac.ncl.CSC8002.b2052238.rentalCarCoursework.InterfaceAbstractCar#getType()
	 */
	public TypeOfCar getType() {
		return typeOfCar;
	}

}
