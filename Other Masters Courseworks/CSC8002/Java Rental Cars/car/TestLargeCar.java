package uk.ac.ncl.CSC8002.b2052238.car;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

public class TestLargeCar {

	@Test
	public void testLargeCar() {
	}

	@Test
	public void testLargedrive() {
		InterfaceAbstractCar d = AbstractCar.getInstance("large");
		assertTrue(0 == d.drive(15));
		d.changeCarRented();
		assertTrue(0 == d.drive(0));
		assertTrue(5 == d.drive(50));
		assertTrue(55 == d.getCurrentFuel());
		assertTrue(1 == d.drive(10));
		assertTrue(54 == d.getCurrentFuel());
		assertTrue(54 == d.drive(780));
		assertTrue(0 == d.getCurrentFuel());
		assertTrue(0 == d.drive(1));
		d.changeCarRented();
		d.fillTankForNewRental();
		d.changeCarRented();
		assertTrue(2 == d.drive(14));
		assertTrue(58 == d.getCurrentFuel());
		assertTrue(3 == d.drive(29));
		assertTrue(55 == d.getCurrentFuel());
		assertTrue(6 == d.drive(51));
		assertTrue(49 == d.getCurrentFuel());
	}

	@After
	public void tearDown() throws Exception {
		RegFactory.resetTesting();
	}
}