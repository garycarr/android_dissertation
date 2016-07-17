package uk.ac.ncl.CSC8002.b2052238.company;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import uk.ac.ncl.CSC8002.b2052238.car.AbstractCar;
import uk.ac.ncl.CSC8002.b2052238.car.InterfaceAbstractCar;
import uk.ac.ncl.CSC8002.b2052238.driver.DriversLicense;
import uk.ac.ncl.CSC8002.b2052238.driver.NameFactory;
import uk.ac.ncl.CSC8002.b2052238.driver.WrapperDate;

public class TestCarRental {

	@Test
	public void testCarRental() {
	}

	@Test
	public void testAddCarToCarRental() {
		CarRental com = CarRental.getInstance();
		com.resetTesting();
		final InterfaceAbstractCar a = AbstractCar.getInstance("Small");
		final InterfaceAbstractCar b = AbstractCar.getInstance("Small");
		final InterfaceAbstractCar c = AbstractCar.getInstance("Large");
		com.addCarToCarRental(a);
		com.addCarToCarRental(b);
		com.addCarToCarRental(c);
		assertTrue(1 == com.availableCars("Large"));
		assertTrue(2 == com.availableCars("Small"));

	}

	@Test
	public void testIssueCar() {

		CarRental com = CarRental.getInstance();
		com.resetTesting();
		final InterfaceAbstractCar a = AbstractCar.getInstance("Small");
		final InterfaceAbstractCar b = AbstractCar.getInstance("Small");
		final InterfaceAbstractCar c = AbstractCar.getInstance("Large");
		com.addCarToCarRental(a);
		com.addCarToCarRental(b);
		com.addCarToCarRental(c);
		WrapperDate d = new WrapperDate(12, 5, 1989);
		WrapperDate e = new WrapperDate(1, 1, 2004);
		Date f = d.getDate();
		Date g = e.getDate();
		NameFactory name = NameFactory.getInstance("Gary", "Carr");
		DriversLicense w = new DriversLicense(name, f, g, true);
		String t = "Small";

		WrapperDate dd = new WrapperDate(12, 5, 1989);
		WrapperDate ee = new WrapperDate(1, 1, 2004);
		Date ff = dd.getDate();
		Date gg = ee.getDate();
		name = NameFactory.getInstance("Gary", "Carr");
		DriversLicense ww = new DriversLicense(name, f, g, false);
		String tt = "Small";

		assertTrue(-1 == com.issueCar(ww, tt));

		com.issueCar(w, t);
		assertTrue(1 == com.availableCars("Small"));
		assertTrue(1 == com.availableCars("Large"));

		d = new WrapperDate(12, 5, 1984);
		e = new WrapperDate(1, 1, 2004);
		f = d.getDate();
		g = e.getDate();
		name = NameFactory.getInstance("John", "Jones");
		DriversLicense x = new DriversLicense(name, f, g, true);

		String tL = "large";

		com.issueCar(x, tL);
		assertTrue(1 == com.availableCars("Small"));
		assertTrue(0 == com.availableCars("Large"));

		com.terminateRental(w);
		assertTrue(2 == com.availableCars("Small"));
		assertTrue(0 == com.availableCars("Large"));

		String carType = "large";

		assertTrue(true == (0 == com.availableCars(carType)));

		com.terminateRental(x);

		assertTrue(true == (1 == com.availableCars(carType)));

	}

	@Test
	public void testTerminateRental() {
		CarRental com = CarRental.getInstance();
		com.resetTesting();
		final InterfaceAbstractCar a = AbstractCar.getInstance("Small");
		final InterfaceAbstractCar b = AbstractCar.getInstance("Large");
		final InterfaceAbstractCar c = AbstractCar.getInstance("Large");
		com.addCarToCarRental(a);
		com.addCarToCarRental(b);
		com.addCarToCarRental(c);

		WrapperDate d = new WrapperDate(12, 5, 1989);
		WrapperDate e = new WrapperDate(1, 1, 2004);
		Date f = d.getDate();
		Date g = e.getDate();
		NameFactory name = NameFactory.getInstance("Gary", "Carr");
		DriversLicense w = new DriversLicense(name, f, g, true);
		String t = "Small";
		assertTrue(null == com.getDriversCar(w));
		com.issueCar(w, t);
		assertTrue(0 == com.availableCars("Small"));

		InterfaceAbstractCar abCar = com.getDriversCar(w);
		abCar.drive(21);
		assertTrue(47 == abCar.getCurrentFuel());

		assertTrue(2 == com.terminateRental(w));
		assertTrue(1 == com.availableCars("Small"));
		assertTrue(null == com.getDriversCar(w));

		com.issueCar(w, t);
		assertTrue(49 == abCar.getCurrentFuel());
	}

	@Test
	public void testAreCarAvailable() {
		CarRental com = CarRental.getInstance();
		com.resetTesting();
		final InterfaceAbstractCar a = AbstractCar.getInstance("Small");
		final InterfaceAbstractCar b = AbstractCar.getInstance("Small");
		final InterfaceAbstractCar c = AbstractCar.getInstance("Large");
		com.addCarToCarRental(a);
		com.addCarToCarRental(b);
		com.addCarToCarRental(c);

		WrapperDate d = new WrapperDate(12, 5, 1986);
		WrapperDate e = new WrapperDate(1, 1, 2004);
		Date f = d.getDate();
		Date g = e.getDate();
		NameFactory name = NameFactory.getInstance("Gary", "Carr");
		DriversLicense w = new DriversLicense(name, f, g, true);

		String tL = "Large";

		String sL = "small";

		assertTrue(true == (2 == com.availableCars(sL)));
		assertTrue(true == (1 == com.availableCars(tL)));
		com.issueCar(w, tL);

		assertTrue(true == (0 == com.availableCars(tL)));

	}

	@Test
	public void testdriverIsOldEnough() {
		CarRental com = CarRental.getInstance();
		com.resetTesting();
		final InterfaceAbstractCar a = AbstractCar.getInstance("Small");
		final InterfaceAbstractCar b = AbstractCar.getInstance("Small");
		final InterfaceAbstractCar c = AbstractCar.getInstance("Large");
		com.addCarToCarRental(a);
		com.addCarToCarRental(b);
		com.addCarToCarRental(c);

		WrapperDate d = new WrapperDate(7, 3, 1993);
		WrapperDate e = new WrapperDate(1, 1, 2004);
		Date f = d.getDate();
		Date g = e.getDate();
		NameFactory name = NameFactory.getInstance("Gary", "Carr");
		DriversLicense w = new DriversLicense(name, f, g, true);
		assertTrue(1 == com.issueCar(w, "small"));
		com.terminateRental(w);
		assertTrue(-1 == com.issueCar(w, "large"));
		d = new WrapperDate(12, 5, 1986);
		e = new WrapperDate(1, 1, 2004);
		f = d.getDate();
		g = e.getDate();
		name = NameFactory.getInstance("Gary", "Carr");
		DriversLicense x = new DriversLicense(name, f, g, true);
		assertTrue(1 == com.issueCar(x, "small"));
		com.terminateRental(x);
		assertTrue(1 == com.issueCar(x, "large"));
		com.terminateRental(x);

		assertTrue(-1 == com.issueCar(w, "large"));
		assertTrue(1 == com.issueCar(w, "small"));
	}

	@Test
	public void testissueCar() {
		CarRental com = CarRental.getInstance();
		com.resetTesting();
		final InterfaceAbstractCar a = AbstractCar.getInstance("Small");
		final InterfaceAbstractCar b = AbstractCar.getInstance("Small");
		final InterfaceAbstractCar c = AbstractCar.getInstance("Large");
		com.addCarToCarRental(a);
		com.addCarToCarRental(b);
		com.addCarToCarRental(c);

		WrapperDate d = new WrapperDate(12, 5, 1980);
		WrapperDate e = new WrapperDate(1, 1, 2010);
		Date f = d.getDate();
		Date g = e.getDate();
		NameFactory name = NameFactory.getInstance("Gary", "Carr");
		DriversLicense w = new DriversLicense(name, f, g, true);

		String t = "Small";
		String tL = "Large";

		assertTrue(1 == com.issueCar(w, t));
		assertTrue(1 == com.getRentedCars().size());
		com.terminateRental(w);
		assertTrue(0 == com.getRentedCars().size());
		assertTrue(-1 == com.issueCar(w, tL));

		d = new WrapperDate(12, 5, 1986);
		e = new WrapperDate(1, 1, 2004);
		f = d.getDate();
		g = e.getDate();
		name = NameFactory.getInstance("Gary", "Carr");
		DriversLicense x = new DriversLicense(name, f, g, true);

		assertTrue(1 == com.issueCar(x, t));
		com.terminateRental(x);
		assertTrue(1 == com.issueCar(x, tL));
		com.terminateRental(x);

		d = new WrapperDate(12, 5, 1994);
		e = new WrapperDate(1, 1, 2013);
		f = d.getDate();
		g = e.getDate();
		name = NameFactory.getInstance("Gary", "Carr");
		DriversLicense y = new DriversLicense(name, f, g, true);

		assertTrue(-1 == com.issueCar(y, t));// Fails as license
												// under 1 year
		assertTrue(-1 == com.issueCar(y, tL));// Fails as license
												// under 1 year

	}

	@Test
	public void testcanRentAsNotAlreadyRenting() {
		CarRental com = CarRental.getInstance();
		com.resetTesting();
		final InterfaceAbstractCar a = AbstractCar.getInstance("Small");
		final InterfaceAbstractCar b = AbstractCar.getInstance("Small");
		final InterfaceAbstractCar c = AbstractCar.getInstance("Large");
		com.addCarToCarRental(a);
		com.addCarToCarRental(b);
		com.addCarToCarRental(c);

		WrapperDate d = new WrapperDate(12, 5, 1980);
		WrapperDate e = new WrapperDate(1, 1, 2010);
		Date f = d.getDate();
		Date g = e.getDate();
		NameFactory name = NameFactory.getInstance("Gary", "Carr");
		DriversLicense w = new DriversLicense(name, f, g, true);

		String t = "Small";

		com.issueCar(w, t);

		assertTrue(-1 == com.issueCar(w, "small"));
		assertTrue(-1 == com.issueCar(w, "large"));
		com.terminateRental(w);
		assertTrue(1 == com.issueCar(w, "small"));
		assertTrue(-1 == com.issueCar(w, "small"));
		assertTrue(-1 == com.issueCar(w, "large"));
	}

	/**
	 * uk.ac.ncl.CSC8002.b2052238.rentalCarCoursework. JUnit test must be ran
	 * with just TestCarRental for this to pass. If all JUnit tests run
	 * simultaneously the method fails (as license plate will be different).
	 */
	@Test
	public void testGetCar() {
		CarRental com = CarRental.getInstance();
		com.resetTesting();
		final InterfaceAbstractCar a = AbstractCar.getInstance("Small");
		assertTrue("A0003 - Small Car".equals(a.toString()));
		final InterfaceAbstractCar b = AbstractCar.getInstance("Small");
		final InterfaceAbstractCar c = AbstractCar.getInstance("Large");
		assertTrue("A0005 - Large Car".equals(c.toString()));
		com.addCarToCarRental(a);
		com.addCarToCarRental(b);
		com.addCarToCarRental(c);

		WrapperDate d = new WrapperDate(12, 5, 1989);
		WrapperDate e = new WrapperDate(1, 1, 2004);
		Date f = d.getDate();
		Date g = e.getDate();
		NameFactory name = NameFactory.getInstance("Gary", "Carr");
		DriversLicense w = new DriversLicense(name, f, g, true);
		String t = "Small";

		d = new WrapperDate(12, 5, 1980);
		e = new WrapperDate(1, 1, 2004);
		f = d.getDate();
		g = e.getDate();
		name = NameFactory.getInstance("Gaz", "Carr");
		DriversLicense j = new DriversLicense(name, f, g, true);
		String tL = "Large";

		com.issueCar(w, t);
		com.issueCar(j, tL);

		assertTrue(a == com.getDriversCar(w));
		assertTrue(c == com.getDriversCar(j));
		assertTrue("A0003 - Small Car".equals(com.getDriversCar(w).toString()));
		assertTrue("A0005 - Large Car".equals(com.getDriversCar(j).toString()));

	}

	@Test
	public void testGetRentedCarNumbers() {
		CarRental com = CarRental.getInstance();
		com.resetTesting();
		final InterfaceAbstractCar a = AbstractCar.getInstance("Small");
		final InterfaceAbstractCar b = AbstractCar.getInstance("Small");
		final InterfaceAbstractCar c = AbstractCar.getInstance("Large");
		com.addCarToCarRental(a);
		com.addCarToCarRental(b);
		com.addCarToCarRental(c);

		WrapperDate d = new WrapperDate(12, 5, 1989);
		WrapperDate e = new WrapperDate(1, 1, 2004);
		Date f = d.getDate();
		Date g = e.getDate();
		NameFactory name = NameFactory.getInstance("Gary", "Carr");
		DriversLicense w = new DriversLicense(name, f, g, true);

		d = new WrapperDate(12, 5, 1980);
		e = new WrapperDate(1, 1, 2004);
		f = d.getDate();
		g = e.getDate();
		name = NameFactory.getInstance("Gary", "Carr");
		DriversLicense y = new DriversLicense(name, f, g, true);

		assertTrue(0 == com.getRentedCars().size());
		String t = "Small";

		com.issueCar(w, t);
		assertTrue(1 == com.getRentedCars().size());
		com.issueCar(y, t);
		assertTrue(2 == com.getRentedCars().size());

		com.terminateRental(w);
		// assertTrue(1 == com.getNumberOfRentedCars());

	}

	@Test(expected = IllegalArgumentException.class)
	public void validateStringIssueCar() {
		CarRental com = CarRental.getInstance();
		WrapperDate d = new WrapperDate(12, 5, 1986);
		WrapperDate e = new WrapperDate(1, 1, 2004);
		Date f = d.getDate();
		Date g = e.getDate();
		final NameFactory name = NameFactory.getInstance("Gary", "Carr");
		DriversLicense w = new DriversLicense(name, f, g, true);

		com.issueCar(w, "Smal");
	}

	@Test(expected = IllegalArgumentException.class)
	public void validateStringAvailableCars() {
		CarRental com = CarRental.getInstance();
		com.availableCars("Smal");
	}

	@Test
	public void testgetNoOfAvailableCars() {
		CarRental com = CarRental.getInstance();
		com.resetTesting();
		final InterfaceAbstractCar a = AbstractCar.getInstance("Small");
		final InterfaceAbstractCar b = AbstractCar.getInstance("Small");
		final InterfaceAbstractCar c = AbstractCar.getInstance("Large");
		com.addCarToCarRental(a);
		com.addCarToCarRental(b);
		com.addCarToCarRental(c);

		WrapperDate d = new WrapperDate(12, 5, 1986);
		WrapperDate e = new WrapperDate(1, 1, 2004);
		Date f = d.getDate();
		Date g = e.getDate();
		final NameFactory name = NameFactory.getInstance("Gary", "Carr");
		DriversLicense w = new DriversLicense(name, f, g, true);

		assertTrue(2 == com.availableCars("Small"));
		assertTrue(1 == com.availableCars("Large"));

		String t = "Small";

		com.issueCar(w, t);
		assertTrue(1 == com.availableCars("Small"));

	}

}
