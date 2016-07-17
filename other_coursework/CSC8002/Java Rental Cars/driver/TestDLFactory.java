package uk.ac.ncl.CSC8002.b2052238.driver;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

/**
 * @author b2052238
 * 
 */
public class TestDLFactory {

	/**
	 * Through number incrementing the license plate should never duplicate. To
	 * test exception works the line DLIST.clear() needs to be commented out of
	 * the DLFactory method resetTesting
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testDuplicateDLicenses() {
		DLFactory.resetTesting();
		NameFactory n = NameFactory.getInstance("Gary", "Carr");
		WrapperDate j = new WrapperDate(12, 5, 2007);
		Date i = j.getDate();
		DLFactory d = DLFactory.getInstance(n, i);
		DLFactory.resetTestingDuplicates();
		d = DLFactory.getInstance(n, i);

	}

	/**
	 * Test method for {@link rentalCarCoursework.DLFactory#getDLicense()}.
	 */
	@Test
	public void testGetDLicense() {
		DLFactory.resetTesting();
		NameFactory n = NameFactory.getInstance("Gary", "Carr");
		WrapperDate j = new WrapperDate(12, 5, 2008);
		Date i = j.getDate();

		DLFactory d = DLFactory.getInstance(n, i);
		assertTrue("GC-2008-00001".equals(d.getDriversLicense()));
		d = DLFactory.getInstance(n, i);
		assertTrue("GC-2008-00002".equals(d.getDriversLicense()));

	}

	/**
	 * Test method for {@link rentalCarCoursework.DLFactory#toString()}.
	 */
	@Test
	public void testToString() {
		DLFactory.resetTesting();
		NameFactory n = NameFactory.getInstance("Gary", "Carr");
		WrapperDate j = new WrapperDate(12, 5, 2008);
		Date i = j.getDate();

		DLFactory d = DLFactory.getInstance(n, i);
		assertTrue("Gary Carr - 12/5/2008".equals(d.toString()));

		DLFactory.resetTesting();
		n = NameFactory.getInstance("Gary", "Carr");
		j = new WrapperDate(1, 1, 1980);
		i = j.getDate();

		d = DLFactory.getInstance(n, i);
		assertTrue("Gary Carr - 1/1/1980".equals(d.toString()));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testValueOfFutureDate() {
		DLFactory.valueOf("Gary Carr - 1/1/2014");
	}

	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void testValueOfOutOfBoundsGreater() {
		DLFactory.valueOf("Gary Carr - 1/1/2008 - Test");
	}

	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void testValueOfOutOfBoundsLess() {
		DLFactory.valueOf("Gary Carr");
	}

}
