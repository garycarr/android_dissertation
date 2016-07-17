package uk.ac.ncl.CSC8002.b2052238.car;

import java.util.Calendar;
import java.util.Date;

/**
 * Abstract class instantiates a new Object of subclass cars. Provides methods
 * to modify those objects and implements InterfaceAbstractCar
 * 
 * @author Gary Carr - Class creates a car
 * 
 */
public abstract class AbstractCar implements InterfaceAbstractCar {

	private final int capacity;
	private int currentFuel;
	private final RegFactory reg;
	private boolean carRented;

	/**
	 * Constructor instantiates a new car of a subclass type. The total capacity
	 * of the car is stored, the constructor calls on RegFactory to create a new
	 * Registration and assigns it to the car, and sets the current fuel to
	 * match the capacity of the car.
	 * 
	 * @param capacity
	 *            The cars total tank capacity, passed by the subclasses
	 * @param typeofcar
	 */
	AbstractCar(int capacity) {
		this.capacity = capacity;
		this.currentFuel = capacity;
		this.reg = RegFactory.getInstance();
	}

	/**
	 * Returns a new Small or Large Car dependent on the String entered by the
	 * user
	 * 
	 * @param type
	 *            String must be either "small" or "large" to match availabe
	 *            cars. It is trimmed and changed to lower case in the method
	 * @return Returns a new car of the specified type
	 * @throws IllegalArgumentException
	 *             If the user does not type "small" or "large"
	 * @throws NullPointerException
	 *             If string is not typed
	 */
	public static AbstractCar getInstance(String type) {
		String typeCar = type.toLowerCase().trim();

		if (typeCar.equals("small")) {
			return new SmallCar();
		}
		if (typeCar.equals("large")) {
			return new LargeCar();
		} else {
			throw new IllegalArgumentException("Incorrect car type specificied");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ncl.CSC8002.b2052238.rentalCarCoursework.InterfaceAbstractCar#
	 * getRegistrationNumber()
	 */
	public String getRegistrationNumber() {
		return reg.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ncl.CSC8002.b2052238.rentalCarCoursework.InterfaceAbstractCar#
	 * getCapacity()
	 */
	public int getCapacity() {
		return capacity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ncl.CSC8002.b2052238.rentalCarCoursework.InterfaceAbstractCar#
	 * getCurrentFuel()
	 */
	public int getCurrentFuel() {
		return currentFuel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ncl.CSC8002.b2052238.rentalCarCoursework.InterfaceAbstractCar#
	 * isTankFull()
	 */
	public boolean isTankFull() {
		return currentFuel == getCapacity();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ncl.CSC8002.b2052238.rentalCarCoursework.InterfaceAbstractCar#
	 * isCarRented()
	 */
	public boolean isCarRented() {
		return carRented;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ncl.CSC8002.b2052238.rentalCarCoursework.InterfaceAbstractCar#
	 * changeCarRented()
	 */
	public void changeCarRented() {
		if (isCarRented()) {
			carRented = false;
		} else {
			carRented = true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.ncl.CSC8002.b2052238.rentalCarCoursework.InterfaceAbstractCar#addPetrol
	 * (int)
	 */
	public int addPetrol(int petrol) {
		if (!isCarRented() || petrol < 0)
			return 0;

		if (spaceInTank() <= petrol) {
			final int space = spaceInTank();
			currentFuel += spaceInTank();
			return space;
		}

		else {
			currentFuel += petrol;
			return petrol;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ncl.CSC8002.b2052238.rentalCarCoursework.InterfaceAbstractCar#
	 * spaceInTank()
	 */
	public int spaceInTank() {
		return capacity - currentFuel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ncl.CSC8002.b2052238.rentalCarCoursework.InterfaceAbstractCar#
	 * fillTankForNewRental()
	 */
	public int fillTankForNewRental() {
		if (carRented)
			return -1;
		currentFuel = capacity;
		return 1;
	}

	/**
	 * Method tests if the car is in a state where it can be driven. The car
	 * must be rented out, the distance entered must be greater than zero, and
	 * petrol must be in the tank
	 * 
	 * @param distance
	 *            The distance of the journey, must be greater than zero
	 * @return Returns true if the car is rented, the fuel in the tank is
	 *         greater than zero, and the distance is greater than zero
	 */
	boolean checkIfCanDrive(int distance) {
		return isCarRented() && getCurrentFuel() > 0 && distance > 0;
	}

	/**
	 * Method subtracts the current fuel by the amount of fuel used in a journey
	 * 
	 * @param totalConsumption
	 *            The amount of fuel used in the drivers journey
	 */
	void amountOfFuelUsed(int consumption) {
		currentFuel -= consumption;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ncl.CSC8002.b2052238.rentalCarCoursework.InterfaceAbstractCar#
	 * isOldEnough(int, java.util.Date)
	 */
	public boolean isOldEnough(Date date, int noOfYears) {
		final Date newDate = new Date(date.getTime());
		final Calendar calDate = Calendar.getInstance();
		calDate.setTime(newDate);

		final Calendar now = Calendar.getInstance();
		now.add(Calendar.YEAR, -noOfYears);

		return calDate.compareTo(now) <= 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return reg.toString();
	}
}