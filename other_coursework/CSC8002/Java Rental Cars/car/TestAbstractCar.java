/**
 * 
 */
package uk.ac.ncl.CSC8002.b2052238.car;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author b2052238
 * 
 */
public class TestAbstractCar {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		RegFactory.resetTesting();
	}

	/**
	 * Test method for {@link rentalCarCoursework.Car#AbstractCar.getInstance()}
	 */
	@Test
	public void testGetInstance() {
		RegFactory.resetTesting();
		InterfaceAbstractCar c = AbstractCar.getInstance("Small ");
		assertTrue("A0000".equals(c.getRegistrationNumber().toString()));
		assertTrue(49 == (c.getCapacity()));
		InterfaceAbstractCar l = AbstractCar.getInstance(" Large");
		assertTrue("A0001".equals(l.getRegistrationNumber().toString()));
		assertTrue(60 == (l.getCapacity()));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testStringGetInstance() {
		AbstractCar.getInstance("Smal");
	}

	/**
	 * Test method for
	 * {@link rentalCarCoursework.Car#AbstractCar.getRegistrationNumber()
	 * .toString()}.
	 */
	@Test
	public void testgetRegistrationNumber() {
		RegFactory.resetTesting();
		InterfaceAbstractCar c = AbstractCar.getInstance("Small");
		assertTrue("A0000".equals(c.getRegistrationNumber().toString()));

		InterfaceAbstractCar d = AbstractCar.getInstance("Small");
		assertTrue("A0001".equals(d.getRegistrationNumber().toString()));
	}

	/**
	 * Test method for {@link rentalCarCoursework.Car#AbstractCar.getCapacity()}
	 * .
	 */
	@Test
	public void testCapacity() {

		InterfaceAbstractCar c = AbstractCar.getInstance("Small ");
		assertTrue(49 == (c.getCapacity()));
		InterfaceAbstractCar l = AbstractCar.getInstance(" Large");
		assertTrue(60 == (l.getCapacity()));
	}

	/**
	 * Test method for
	 * {@link rentalCarCoursework.Car#AbstractCar.getCurrentFuel()}.
	 */
	@Test
	public void testGetCurrentFuel() {

		InterfaceAbstractCar c = AbstractCar.getInstance("Small");
		assertTrue(49 == c.getCurrentFuel());
		c.changeCarRented();
		c.drive(500);
		assertTrue(24 == c.getCurrentFuel());
		c.changeCarRented();
		c.fillTankForNewRental();
		assertTrue(49 == c.getCurrentFuel());

	}

	/**
	 * Test method for {@link rentalCarCoursework.Car#reduceCurrentFuel(int)}.
	 */
	@Test
	public void testReduceCurrentFuel() {

		InterfaceAbstractCar c = AbstractCar.getInstance("Small");
		c.changeCarRented();
		c.drive(100);
		assertTrue(44 == c.getCurrentFuel());
	}

	/**
	 * Test method for {@link rentalCarCoursework.Car#isTankFull()}.
	 */
	@Test
	public void testIsTankFull() {

		InterfaceAbstractCar c = AbstractCar.getInstance("Small");
		assertTrue(true == c.isTankFull());
		c.changeCarRented();
		c.drive(500);
		assertTrue(false == c.isTankFull());
		c.changeCarRented();
		c.fillTankForNewRental();
		assertTrue(true == c.isTankFull());
	}

	/**
	 * Test method for {@link rentalCarCoursework.Car#isCarRented()}.
	 */
	@Test
	public void testIsCarRented() {

		InterfaceAbstractCar c = AbstractCar.getInstance("Small");
		c.changeCarRented();
		assertTrue(true == c.isCarRented());
		c.changeCarRented();
		assertTrue(false == c.isCarRented());
		c.changeCarRented();
		assertTrue(true == c.isCarRented());
	}

	/**
	 * Test method for {@link rentalCarCoursework.Car#changeCarRented()}.
	 */
	@Test
	public void testChangeCarRented() {

		InterfaceAbstractCar c = AbstractCar.getInstance("Small");
		assertTrue(false == c.isCarRented());
		c.changeCarRented();
		assertTrue(true == c.isCarRented());

	}

	/**
	 * Test method for {@link rentalCarCoursework.Car#addPetrol(int)}.
	 */
	@Test
	public void testAddPetrol() {

		InterfaceAbstractCar c = AbstractCar.getInstance("Small");
		assertTrue(0 == c.addPetrol(15));
		c.changeCarRented();
		assertTrue(0 == c.addPetrol(0));
		c.drive(70);
		assertTrue(45 == c.getCurrentFuel());
		assertTrue(1 == c.addPetrol(1));
		assertTrue(46 == c.getCurrentFuel());
		assertTrue(3 == c.addPetrol(70));
	}

	/**
	 * Test method for {@link rentalCarCoursework.Car#spaceInTank()}.
	 */
	@Test
	public void testSpaceInTank() {

		InterfaceAbstractCar d = AbstractCar.getInstance("Small");
		assertTrue(d.spaceInTank() == 0);
		d.changeCarRented();
		d.drive(21);
		assertTrue(d.spaceInTank() == 2);
		d.changeCarRented();
		d.fillTankForNewRental();
		assertTrue(d.spaceInTank() == 0);
	}

	/**
	 * Test method for {@link rentalCarCoursework.Car#fillTankForRental() .
	 */
	@Test
	public void testFillTankForRental() {

		InterfaceAbstractCar d = AbstractCar.getInstance("Small");
		assertTrue(d.fillTankForNewRental() == 1);
		d.changeCarRented();
		d.drive(21);
		assertTrue(d.fillTankForNewRental() == -1);
		d.changeCarRented();
		assertTrue(d.fillTankForNewRental() == 1);
	}

	/**
	 * Test method for {@link rentalCarCoursework.Car#checkIfCanDrive() .
	 */
	@Test
	public void testCheckIfCanDrive() {

		SmallCar c = new SmallCar();
		assertTrue(false == c.checkIfCanDrive(50));
		c.changeCarRented();
		assertTrue(false == c.checkIfCanDrive(0));
		c.drive(500000);
		assertTrue(false == c.checkIfCanDrive(10));
		c.changeCarRented();
		c.fillTankForNewRental();
		assertTrue(false == c.checkIfCanDrive(500));
	}

	/**
	 * Test method for {@link rentalCarCoursework.Car#amountOfFuelUsed() .
	 */
	@Test
	public void testAmountOfFuelUsed() {
		SmallCar c = new SmallCar();
		c.changeCarRented();
		assertTrue(25 == c.drive(500));
		assertTrue(24 == c.getCurrentFuel());
	}

	/**
	 * Test method for {@link rentalCarCoursework.Car#toString()}.
	 */
	@Test
	public void testtoString() {
		RegFactory.resetTesting();
		InterfaceAbstractCar a = AbstractCar.getInstance("Small");
		InterfaceAbstractCar b = AbstractCar.getInstance("large");
		InterfaceAbstractCar c = AbstractCar.getInstance("large");
		assertTrue(a.toString().equals("A0000 - Small Car"));
		assertTrue(b.toString().equals("A0001 - Large Car"));
		assertTrue(c.toString().equals("A0002 - Large Car"));

	}

	@After
	public void tearDown() throws Exception {
		RegFactory.resetTesting();
	}

}
