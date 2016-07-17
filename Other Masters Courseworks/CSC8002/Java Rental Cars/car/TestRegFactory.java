package uk.ac.ncl.CSC8002.b2052238.car;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

/**
 * @author Gary
 * 
 */
public class TestRegFactory {

	/**
	 * Through number incrementing and letter incrementing the plates should
	 * never duplicate. To test exception works the line REGLIST.clear() needs
	 * to be commented out of the RegFactory method RegFactory.resetTesting()
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testDuplicateRegPlates() {
		RegFactory.resetTesting();
		RegFactory a = RegFactory.getInstance();
		RegFactory.resetTestingDuplicates();

		a = RegFactory.getInstance();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMaximumNumberOfPlates() {
		RegFactory.resetTesting();
		RegFactory a = RegFactory.getInstance();
		int count = 0;
		assertTrue("A0000".equals(a.toString()));
		while (count < 250000) {
			a = RegFactory.getInstance();
			count++;
		}

		assertTrue("Z0000".equals(a.toString()));
		count = 0;
		while (count < 9999) {
			a = a.getInstance();
			count++;
		}
		assertTrue("Z9999".equals(a.toString()));
		a.getInstance();
	}

	@Test
	public void testIncrementNumber() {
		RegFactory.resetTesting();
		RegFactory a = RegFactory.getInstance();
		int i = 0;
		while (i < 9998) {
			a.getInstance();
			i++;
		}
		RegFactory f = RegFactory.getInstance();
		assertTrue("A9999".equals(f.toString()));
		RegFactory g = RegFactory.getInstance();
		assertTrue("B0000".equals(g.toString()));
	}

	@Test
	public void testIncrementLetters() {
		RegFactory.resetTesting();

		RegFactory a = RegFactory.getInstance();
		int i = 0;
		while (i < 10000) {
			a.getInstance();
			i++;
		}
		a = RegFactory.getInstance();
		assertTrue("B0001".equals(a.toString()));
		RegFactory.resetTesting();

		int count = 0;

		while (count < 250000) {
			a = RegFactory.getInstance();
			count++;
		}
		a = RegFactory.getInstance();
		assertTrue("Z0000".equals(a.toString()));

		count = 0;
		while (count < 9999) {
			a = a.getInstance();
			count++;
		}
		assertTrue("Z9999".equals(a.toString()));

	}

	@Test
	public void testGetLetter() {
		RegFactory.resetTesting();
		RegFactory a = RegFactory.getInstance();
		assertTrue("A".equals(a.getLetter()));
		a = RegFactory.getInstance();
		assertTrue("A".equals(a.getLetter()));
		int i = 0;
		while (i < 10000) {
			a.getInstance();
			i++;
		}
		a = RegFactory.getInstance();
		assertTrue("B".equals(a.getLetter()));
	}

	@Test
	public void testGetNumber() {
		RegFactory.resetTesting();
		RegFactory a = RegFactory.getInstance();
		assertTrue("0000".equals(a.getNumber()));
		a = RegFactory.getInstance();
		assertTrue("0001".equals(a.getNumber()));
		int i = 0;
		while (i < 10000) {
			a.getInstance();
			i++;
		}
		a = RegFactory.getInstance();
		assertTrue("0002".equals(a.getNumber()));
	}

	@Test
	public void testToString() {
		RegFactory.resetTesting();

		RegFactory r = RegFactory.getInstance();
		assertTrue("A0000".equals(r.toString()));

		RegFactory f = RegFactory.getInstance();
		assertTrue("A0001".equals(f.toString()));

	}

	@After
	public void tearDown() throws Exception {
		RegFactory.resetTesting();
	}

}
