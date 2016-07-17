/**
 * 
 */
package uk.ac.ncl.CSC8002.b2052238.car;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

/**
 * @author b2052238
 * 
 */
public final class TestSmallCar {

	/**
	 * Test method for {@link rentalCarCoursework.SmallCar#SmallCar()}.
	 */
	@Test
	public void testSmallCar() {
		InterfaceAbstractCar c = AbstractCar.getInstance("small");
		assertTrue(0 == c.drive(15));
		c.changeCarRented();
		assertTrue(0 == c.drive(0));
		assertTrue(3 == c.drive(50));
		assertTrue(46 == c.getCurrentFuel());
		assertTrue(5 == c.drive(100));
		assertTrue(41 == c.getCurrentFuel());
		c.changeCarRented();
		c.fillTankForNewRental();
		c.changeCarRented();
		assertTrue(10 == c.drive(200));

		assertTrue(39 == c.getCurrentFuel());
		assertTrue(1 == c.drive(1));
		assertTrue(38 == c.getCurrentFuel());
		assertTrue(1 == c.drive(15));
		assertTrue(2 == c.drive(21));

	}

	@After
	public void tearDown() throws Exception {
		RegFactory.resetTesting();
	}
}
