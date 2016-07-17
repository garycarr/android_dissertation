package uk.ac.ncl.CSC8002.b2052238.company;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.ncl.CSC8002.b2052238.car.AbstractCar;
import uk.ac.ncl.CSC8002.b2052238.car.InterfaceAbstractCar;
import uk.ac.ncl.CSC8002.b2052238.car.TypeOfCar;
import uk.ac.ncl.CSC8002.b2052238.driver.DriversLicense;
import uk.ac.ncl.CSC8002.b2052238.driver.NameFactory;
import uk.ac.ncl.CSC8002.b2052238.driver.WrapperDate;

/**
 * CarRental - Allows the rental car company to access the public fields of
 * InterfaceAbstractCar and DriversLicense to create lists of cars and maintain
 * which drivers are renting those cars.
 * 
 * @author Gary Carr
 */
public final class CarRental {

	private static final CarRental INSTANCE = new CarRental();
	private List<InterfaceAbstractCar> companyCars;
	private Map<DriversLicense, InterfaceAbstractCar> rentDetails;

	/**
	 * Constructor creates an ArrayList to store all the companies cars owned by
	 * the company, and a HashMap to link the drivers to the cars they are
	 * renting
	 */
	private CarRental() {
		companyCars = new ArrayList<InterfaceAbstractCar>();
		rentDetails = new HashMap<DriversLicense, InterfaceAbstractCar>();
	}

	/**
	 * Method ensures only one instance of CarRental can be created
	 * 
	 * @return Returns the instance of CarRental
	 */
	public static CarRental getInstance() {
		return INSTANCE;
	}

	/**
	 * Method adds a new car to the ArrayList of company cars.
	 * 
	 * @param car
	 *            A car object to be added to the list
	 */
	public void addCarToCarRental(InterfaceAbstractCar car) {
		companyCars.add(car);
	}

	/**
	 * Method returns the amount of small or large cars available for rent,
	 * dependent on the String entered
	 * 
	 * @param type
	 *            String must be either "small" or "large" to match available
	 *            cars. It is trimmed and changed to lower case in the method
	 * @return Returns the number of cars available for rent of the type
	 *         specified in the String
	 * @throws IllegalArgumentException
	 *             if the user has inputted neither "small" or "large"
	 */
	public int availableCars(String type) {
		String typeCar = type.trim().toLowerCase();
		int count = 0;

		if (typeCar.equals("small")) {
			for (InterfaceAbstractCar car : companyCars) {
				if (car.getType().equals(TypeOfCar.SMALL) && !car.isCarRented()) {

					count++;
				}
			}
		} else if (typeCar.equals("large")) {
			for (InterfaceAbstractCar car : companyCars) {
				if (car.getType().equals(TypeOfCar.LARGE) && !car.isCarRented()) {
					count++;
				}
			}
		} else {
			throw new IllegalArgumentException(
					"Type of car has been entered incorrectly.  Needs to be 'small' or 'large'");
		}

		return count;
	}

	/**
	 * Method returns a collection of all the currently rented cars
	 * 
	 * @return Returns collection of all rented cars
	 */
	public Collection<InterfaceAbstractCar> getRentedCars() {
		return rentDetails.values();
	}

	/**
	 * Method will return the car which the driver is currently renting. If they
	 * are not renting a car, null is returned.
	 * 
	 * @param dl
	 *            DriverLicense object which is checked against the hashmap to
	 *            determine if a car is rented
	 * @return Returns the car being currently rented. If no car is being rented
	 *         null is returned
	 */
	public InterfaceAbstractCar getDriversCar(DriversLicense dl) {
		if (!rentDetails.containsKey(dl))
			return null;
		return rentDetails.get(dl);
	}

	/**
	 * Method checks the drivers license against the specified rules of the
	 * company for the car they wish to rent (inputted as a string). It is
	 * checked if the driver has a full license, if they are already renting, if
	 * they are old enough and have had a license long enough. If all tests pass
	 * then the tank of the car is filled and the details of the driver and car
	 * added to the rentDetails HashMap. The cars status is marked as being
	 * rented.
	 * 
	 * @param dl
	 *            DrivingLicense object is passed to check the drivers details
	 *            to see if they can rent
	 * @param type
	 *            String must be either "small" or "large" to match available
	 *            cars. It is trimmed and changed to lower case in the method
	 * @return Method returns 1 if the issueCar has been successful, and returns
	 *         -1 if the car has not been issued
	 * @throws IllegalArgumentException
	 *             if the user has inputted neither "small" or "large"
	 */
	public int issueCar(DriversLicense dl, String type) {

		if (!(dl.hasFullLicense()))
			return -1;

		if (rentDetails.containsKey(dl))

			return -1;

		if (availableCars(type) == 0)
			return -1;

		InterfaceAbstractCar car = null;

		String typeCar = type.trim().toLowerCase();
		int count = 0;
		boolean found = false;
		if (!(typeCar.equals("small")) && !(typeCar.equals("large"))) {
			throw new IllegalArgumentException(
					"Type of car has been entered incorrectly.  Needs to be 'small' or 'large'");
		}

		if (typeCar.equals("small")) {
			while (count < companyCars.size() && !found) {
				car = companyCars.get(count);
				if (car.getType().equals(TypeOfCar.SMALL) && !car.isCarRented()) {
					found = true;
				}
				count++;
			}
		}

		else {
			while (count < companyCars.size() && !found) {
				car = companyCars.get(count);
				if (car.getType().equals(TypeOfCar.LARGE) && !car.isCarRented()) {
					found = true;
				}
				count++;
			}
		}

		if (!car.isOldEnough(dl.getDateOfBirth(), car.getDrivingAge()))
			return -1;

		if (!car.isOldEnough(dl.getDateOfIssue(), car.getLicenseAge()))
			return -1;

		if (!car.isTankFull())
			car.fillTankForNewRental();

		car.changeCarRented();

		rentDetails.put(dl, car);
		return 1;
	}

	/**
	 * Method checks that the drivers license passed is currently renting a car,
	 * and if so gets the car, changes its status to not rented, removes the
	 * drivers details from the rentDetails HashMap, and then returns the amount
	 * of fuel required to fill the car
	 * 
	 * @param dL
	 *            The driving license is inputed to find the car the customer is
	 *            currently driving and make the methods modifications
	 * @return Returns -1 if the driver is not currently renting. Otherwise
	 *         returns the amount of fuel required to fill the car
	 */
	public int terminateRental(DriversLicense dL) {
		if (!(rentDetails.containsKey(dL)))
			return -1;

		InterfaceAbstractCar car = rentDetails.get(dL);

		car.changeCarRented();

		rentDetails.remove(dL);

		return car.isTankFull() ? 0 : car.spaceInTank();
	}

	/**
	 * Implemented only for JUnit testing, resets the static numbers in the
	 * class to zero
	 */
	void resetTesting() {
		companyCars.clear();
		rentDetails.clear();
	}

	public static void main(String[] args) {
		int count = 0;
		CarRental com = getInstance();
		while (count < 20) {
			final InterfaceAbstractCar car = AbstractCar.getInstance("small");
			com.addCarToCarRental(car);
			count++;
		}
		while (count < 30) {
			final InterfaceAbstractCar car = AbstractCar.getInstance("Large");
			com.addCarToCarRental(car);
			count++;
		}
		System.out.println("Number of cars total is correct if 30 - "
				+ com.companyCars.size());
		System.out
				.println("Number of small cars available to rent is correct if 20 - "
						+ com.availableCars("Small"));
		System.out
				.println("Number of large cars available to rent is correct if 10 - "
						+ com.availableCars("Large"));

		WrapperDate d = new WrapperDate(12, 5, 1985);
		WrapperDate e = new WrapperDate(1, 1, 2004);
		Date f = d.getDate();
		Date g = e.getDate();
		NameFactory name = NameFactory.getInstance("Gary", "Carr");
		DriversLicense a = new DriversLicense(name, f, g, true);

		com.issueCar(a, "small");

		System.out
				.println("Number of small cars available to rent is correct if 19 - "
						+ com.availableCars("Small"));

		System.out.println("Correct car selected if license A0000 - "
				+ com.getDriversCar(a).getRegistrationNumber());

		com.getDriversCar(a).drive(100);
		System.out.println("Correct if terminate rental returns 5 - "
				+ com.terminateRental(a));

		System.out
				.println("Number of small cars available to rent is correct if 20 - "
						+ com.availableCars("Small"));

		count = 0;
		while (count < 10) {
			d = new WrapperDate(12, 5, 1985);
			e = new WrapperDate(1, 1, 2004);
			f = d.getDate();
			g = e.getDate();
			name = NameFactory.getInstance("Gary", "Carr");
			a = new DriversLicense(name, f, g, true);
			com.issueCar(a, "Large");

			count++;
		}
		System.out
				.println("Number of Large cars available to rent is correct if 0 - "
						+ com.availableCars("large"));

		System.out.println("Correct car selected if license A0029 - "
				+ com.getDriversCar(a).getRegistrationNumber());

		System.out
				.println("Correct is -1 (Car cannot be issued as already renting) "
						+ com.issueCar(a, "small"));

		com.getDriversCar(a).drive(500);
		System.out.println("Correct if terminate rental returns 36 - "
				+ com.terminateRental(a));

		d = new WrapperDate(12, 5, 1989);
		e = new WrapperDate(1, 1, 2004);
		f = d.getDate();
		g = e.getDate();
		name = NameFactory.getInstance("Gary", "Carr");
		a = new DriversLicense(name, f, g, true);
		System.out.println("Correct if -1 (Car cannot be issued - age) - "
				+ com.issueCar(a, "Large"));

		d = new WrapperDate(12, 5, 1984);
		e = new WrapperDate(1, 1, 2010);
		f = d.getDate();
		g = e.getDate();
		name = NameFactory.getInstance("Gary", "Carr");
		a = new DriversLicense(name, f, g, true);
		System.out
				.println("Correct if -1 (Car cannot be issued - age of license) - "
						+ com.issueCar(a, "Large"));

		d = new WrapperDate(12, 5, 1984);
		e = new WrapperDate(1, 1, 2010);
		f = d.getDate();
		g = e.getDate();
		name = NameFactory.getInstance("Gary", "Carr");
		a = new DriversLicense(name, f, g, true);
		com.issueCar(a, "Large");

		d = new WrapperDate(12, 5, 1984);
		e = new WrapperDate(1, 1, 2010);
		f = d.getDate();
		g = e.getDate();
		name = NameFactory.getInstance("Gary", "Carr");
		a = new DriversLicense(name, f, g, true);
		System.out
				.println("Correct if -1 (Car cannot be issued - no Large cars available) - "
						+ com.issueCar(a, "Large"));

	}
}
