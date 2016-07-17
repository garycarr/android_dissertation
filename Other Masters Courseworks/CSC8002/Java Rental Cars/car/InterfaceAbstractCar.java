package uk.ac.ncl.CSC8002.b2052238.car;

import java.util.Date;

/**
 * InterfaceAbstractCar - This class contains method that allows for the fields
 * of a car to be accessed and modified.
 * 
 * @author Gary Carr
 * 
 */
public interface InterfaceAbstractCar {

	/**
	 * Method returns the registration object of the car. All numbers are unique
	 * 
	 * @return The cars registration object
	 */
	String getRegistrationNumber();

	/**
	 * Method returns the total capacity of the car
	 * 
	 * @return Returns the capacity of a car
	 */
	int getCapacity();

	/**
	 * Method returns the current fuel level of the car
	 * 
	 * @return the current fuel level
	 */
	int getCurrentFuel();

	/**
	 * @return Returns true if the current fuel level is the same as the total
	 *         capacity
	 */
	boolean isTankFull();

	/**
	 * @return Returns true if the car is currently rented
	 */
	public boolean isCarRented();

	/**
	 * Method adds petrol to the fuel tank. It will return the amount of petrol
	 * that was actually added, note this may not fill the tank to full
	 * capacity.
	 * 
	 * @param Petrol
	 *            The amount of petrol the user wishes to add to the car
	 * @return Returns the amount of petrol that added to the tank. Method will
	 *         return 0 if the car is not currently rented, or if the tank is
	 *         already full.
	 */
	public int addPetrol(int petrol);

	/**
	 * Method calculates the amount of petrol used by the length of a journey
	 * and returns the total consumption as an int.
	 * 
	 * @param distance
	 *            The distance of the journey undertaken.
	 * 
	 * @return Returns the total consumption in litres for the journey. If the
	 *         journey cannot be undertaken due to the car not being rented, no
	 *         fuel in the tank or the distance entered as zero or less, 0 is
	 *         returned
	 * 
	 */
	int drive(int distance);

	/**
	 * Method returns the amount of petrol needed to fill the tank
	 * 
	 * @return space remaining for petrol in the tank
	 */
	public int spaceInTank();

	/**
	 * Method changes the rented boolean status of the car. If the car is
	 * currently rented (true) and the method implemented the boolean is changed
	 * to false, and visa versa.
	 */
	public void changeCarRented();

	/**
	 * Method returns a car object
	 * 
	 * @return Returns a car object
	 */
	InterfaceAbstractCar getCar();

	/**
	 * Method ensures petrol tank is full capacity for a new rental. If the car
	 * is currently rented then the method cannot be ran.
	 * 
	 * @return Returns 1 if the car tank is full. If the car cannot be filled as
	 *         it is currently rented, returns -1.
	 */
	int fillTankForNewRental();

	/**
	 * Method returns the minimum driving age for the type of car the method is
	 * invoked on
	 * 
	 * @return Returns the minimum age a driver has to be
	 */
	int getDrivingAge();

	/**
	 * Method returns the minimum drivers license age for the type of car the
	 * method is invoked on
	 * 
	 * @return Returns the minimum age a drivers license has to be
	 */
	int getLicenseAge();

	/**
	 * Method takes a date and a number of years and determines if a driver is
	 * eligible to drive that car. The two Dates that can be input are the date
	 * of birth, and the date the license was issued. This is then compared to
	 * the number of years old each date must be (held as a static in the
	 * concrete classes) and if the date is old enough true is returned. If it
	 * is the exact date (birthday or date of issue) required true is returned
	 * 
	 * @param date
	 *            The parameter takes a date of birth or date of issue
	 * @param noOfYears
	 *            The parameter takes the number of years required to drive that
	 *            particular car
	 * @return Returns true if the Date is old enough, false is not old enough
	 */
	boolean isOldEnough(Date date, int noOfYears);

	/**
	 * Method checks the car and returns its enum type, held as a static in the
	 * concrete class
	 * 
	 * @return Returns the Enum type of the car
	 */
	TypeOfCar getType();

}
