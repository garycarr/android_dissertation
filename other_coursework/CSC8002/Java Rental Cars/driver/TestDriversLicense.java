/**
 * 
 */
package uk.ac.ncl.CSC8002.b2052238.driver;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import uk.ac.ncl.CSC8002.b2052238.company.CarRental;

/**
 * @author b2052238
 * 
 */
public class TestDriversLicense {

	/**
	 * Test method for
	 * {@link rentalCarCoursework.DriversLicense#checkFullLicense()}.
	 */
	@Test
	public void testCheckFullLicense() {
		DLFactory.resetTesting();
		WrapperDate d = new WrapperDate(12, 5, 1986);
		WrapperDate e = new WrapperDate(1, 1, 2004);
		Date f = d.getDate();
		Date g = e.getDate();
		NameFactory name = NameFactory.getInstance("Gary", "Carr");
		DriversLicense w = new DriversLicense(name, f, g, false);

		assertTrue(false == w.hasFullLicense());

		d = new WrapperDate(12, 5, 1986);
		e = new WrapperDate(1, 1, 2004);
		f = d.getDate();
		g = e.getDate();
		DriversLicense v = new DriversLicense(name, f, g, true);

		assertTrue(true == v.hasFullLicense());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFutureDateBirthday() {
		DLFactory.resetTesting();
		CarRental com = CarRental.getInstance();
		WrapperDate d = new WrapperDate(12, 5, 2013);
		WrapperDate e = new WrapperDate(1, 1, 2004);
		Date f = d.getDate();
		Date g = e.getDate();
		final NameFactory name = NameFactory.getInstance("Gary", "Carr");
		DriversLicense w = new DriversLicense(name, f, g, true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFutureDateIssue() {
		DLFactory.resetTesting();
		CarRental com = CarRental.getInstance();
		WrapperDate d = new WrapperDate(12, 5, 1986);
		WrapperDate e = new WrapperDate(1, 1, 2014);
		Date f = d.getDate();
		Date g = e.getDate();
		final NameFactory name = NameFactory.getInstance("Gary", "Carr");
		DriversLicense w = new DriversLicense(name, f, g, true);
	}

	/**
	 * Test method for {@link rentalCarCoursework.DriversLicense#getName()}.
	 */
	@Test
	public void testGetName() {
		DLFactory.resetTesting();
		WrapperDate d = new WrapperDate(12, 5, 1986);
		WrapperDate e = new WrapperDate(1, 1, 2004);
		Date f = d.getDate();
		Date g = e.getDate();
		NameFactory name = NameFactory.getInstance("Gary", "Carr");
		DriversLicense w = new DriversLicense(name, f, g, false);
		assertTrue("Gary Carr".equals(w.getName().toString()));
	}

	/**
	 * Test method for
	 * {@link rentalCarCoursework.DriversLicense#getDateOfBirth()}.
	 */
	@Test
	public void testGetDateOfBirth() {
		DLFactory.resetTesting();
		WrapperDate d = new WrapperDate(12, 5, 1986);
		WrapperDate e = new WrapperDate(1, 1, 2004);
		Date f = d.getDate();
		Date g = e.getDate();
		NameFactory name = NameFactory.getInstance("Gary", "Carr");
		DriversLicense w = new DriversLicense(name, f, g, false);
		assertTrue(f.toString().equals(w.getDateOfBirth().toString()));
		assertFalse(g.toString().equals(w.getDateOfBirth().toString()));

	}

	/**
	 * Test method for
	 * {@link rentalCarCoursework.DriversLicense#getDateOfIssue()}.
	 */
	@Test
	public void testGetDateOfIssue() {
		DLFactory.resetTesting();
		WrapperDate d = new WrapperDate(12, 5, 1986);
		WrapperDate e = new WrapperDate(1, 1, 2004);
		Date f = d.getDate();
		Date g = e.getDate();
		NameFactory name = NameFactory.getInstance("Gary", "Carr");
		DriversLicense w = new DriversLicense(name, f, g, false);
		assertTrue(g.toString().equals(w.getDateOfIssue().toString()));
		assertFalse(f.toString().equals(w.getDateOfIssue().toString()));
	}

	/**
	 * Test method for {@link rentalCarCoursework.DriversLicense#toString()}.
	 */
	@Test
	public void testToString() {
		DLFactory.resetTesting();
		WrapperDate d = new WrapperDate(12, 5, 1986);
		WrapperDate e = new WrapperDate(1, 1, 2004);
		Date f = d.getDate();
		Date g = e.getDate();
		NameFactory n = NameFactory.getInstance("Gary", "Carr");
		DriversLicense w = new DriversLicense(n, f, g, false);
		assertTrue("Gary Carr - GC-2004-00001".equals(w.toString()));
	}
}
