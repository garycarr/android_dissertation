/**
 * 
 */
package uk.ac.ncl.CSC8002.b2052238.driver;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Gary Carr
 * 
 */
public class TestName {

	/**
	 * Test method for
	 * {@link rentalCarCoursework.NameFactory#Name(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testName() {

		NameFactory a = NameFactory.getInstance("Gary", "Carr");

		assertTrue("Gary".equals(a.getFName()));
		assertTrue("Carr".equals(a.getLName()));

		NameFactory b = NameFactory.getInstance("Gar", "Carr");
		assertTrue("Gar".equals(b.getFName()));
		assertTrue("Carr".equals(b.getLName()));
	}

	/**
	 * Test method for {@link rentalCarCoursework.NameFactory#getFName()}.
	 */
	@Test
	public void testGetFName() {
		NameFactory a = NameFactory.getInstance("Gary", "Carr");
		assertTrue("Gary".equals(a.getFName()));
		NameFactory b = NameFactory.getInstance("James", "Carr");
		assertTrue("James".equals(b.getFName()));
		NameFactory c = NameFactory.getInstance("Tony", "Carr");
		assertTrue("Tony".equals(c.getFName()));
	}

	/**
	 * Test method for {@link rentalCarCoursework.NameFactory#getLName()}.
	 */
	@Test
	public void testGetLName() {
		NameFactory a = NameFactory.getInstance("Gary", "Carr");
		assertTrue("Carr".equals(a.getLName()));
		NameFactory b = NameFactory.getInstance("James", "Jones");
		assertTrue("Jones".equals(b.getLName()));
		NameFactory c = NameFactory.getInstance("Tony", "Smith");
		assertTrue("Smith".equals(c.getLName()));
	}

	@Test
	public void testValueof() {
		assertTrue("Gary Carr".equals(NameFactory.valueOf("Gary Carr")
				.toString()));
	}

	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void testNamePartsLess() {

		NameFactory.valueOf("Gary");
	}

	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void testNamePartsGreater() {

		NameFactory.valueOf("Gary And Carr");
	}
}
