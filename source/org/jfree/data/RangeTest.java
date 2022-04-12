package org.jfree.data;

import java.security.InvalidParameterException;
import static org.junit.Assert.*;

import java.security.InvalidParameterException;

import org.jfree.data.Range;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

//import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

public class RangeTest extends TestCase {
	
	private Range rangeObjectUnderTest;
	private Range uninitialisedRange;
	private double uninitialisedDouble;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	protected void setUp() throws Exception {
		rangeObjectUnderTest = new Range(0, 10);
	}

	@After
	protected void tearDown() throws Exception {
	}

	@Test
	public void testCentralValueShouldBeZero() {
		assertEquals("The central value of 0 and 10 should be 5", 5, rangeObjectUnderTest.getCentralValue(), 0.000000001d);
	}
	
	@Test
	public void testGetLength() {
		Range r1 = new Range(2, 2);
		assertEquals("getLength: Did not return the expected output", 0.0, r1.getLength());
		
		Range r2 = new Range(4, 9);
		assertEquals("getLength: Did not return the expected output", 5.0, r2.getLength());
		
		Range r3 = new Range(-99, -99);
		assertEquals("getLength: Did not return the expected output", 0.0, r3.getLength());
		
		Range r4 = new Range(-11, -4);
		assertEquals("getLength: Did not return the expected output", 7.0, r4.getLength());
		
		Range r5 = new Range(-5, 3);
		assertEquals("getLength: Did not return the expected output", 8.0, r5.getLength());
	}
	
	//Testing the Contains Method
	
	@Test
	public void testContainsTrue() {
		//Act
		boolean result = rangeObjectUnderTest.contains(5);
		//Assert
		assertEquals("testContainsTrue: Should find 5 in range 0-10",true, result);
	}
	
	@Test
	public void testContainsFalse() {
		//Act
		boolean result = rangeObjectUnderTest.contains(50);
		//Assert
		assertEquals("testContainsFalse: Shouldn't find 50 in range 0-10",false, result);
	}
	
	@Test
	public void testContainsMax() {
		//Act
		boolean result = rangeObjectUnderTest.contains(Double.POSITIVE_INFINITY);
		//Assert
		assertEquals("testContainsMax: Shouldnt find max double in range 0-10",false, result);
	}
	
	@Test
	public void testContainsTooBig() {
		//Act
		boolean result = rangeObjectUnderTest.contains(Double.POSITIVE_INFINITY+1);
		//Assert
		assertEquals("testContainsTooBig: Shouldnt find max double+1 in range 0-10",false, result);
	}
	
	// Testing the Intersect method
	
	//This method results in an error - should fail instead
	//This method should fail as it doesn't give the expected result
	@Test
	public void testIntersectTrue() {
		//Act
		boolean result = rangeObjectUnderTest.intersects(5, 15);
		//Assert
		assertEquals("testIntersectTrue: Expected range 5-15 to overlap range 0-10",true, result);
	}
	
	@Test
	public void testIntersectFalse() {
		//Act
		boolean result = rangeObjectUnderTest.intersects(11, 20);
		//Assert
		assertEquals("testIntersectFalse: Not expecting range 11-20 to overlap range 0-10",false, result);
	}
	
	@Test
	public void testIntersectLargerLowerBound() {
		//Act
		boolean result = rangeObjectUnderTest.intersects(20, 11);
		//Assert
		assertEquals("testIntersectFalse: Not expecting range 20-11 to overlap range 0-10",false, result);
	}
	
	@Test
	public void testIntersectEntireRange() {
		//Act
		boolean result = rangeObjectUnderTest.intersects(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		//Assert
		assertEquals("testIntersectFalse: Expecting range 4.9E-324 - 1.7976931348623157E308 to overlap range 0-10",true, result);
	}
	//Testing the GetLowerBound Method
	
	@Test
	public void testGetLowerBound() {
		//Act
		double result = rangeObjectUnderTest.getLowerBound();
		//Assert
		assertEquals("testGetLowerBound: expected lower bound to be the same as initialised",0.0, result);
	}
	
	// Testing the GetUpperBound Method
	
	//This method results in an error - should fail instead
	//This method should fail as the actual result is different from the expected result
	@Test
	public void testGetUpperBound() {
		//Act
		double result = rangeObjectUnderTest.getUpperBound();
		//Assert
		assertEquals("testGetUpperBound: expected upper bound of range 0-10 to be 10",10.0, result);
	}
	
	//Testing the Expand Method
	
	@Test
	public void testExpandValid() {
		Range expanded = Range.expand(new Range(2, 6), 0.25, 0.5);
		Range expectedExpanded = new Range(1, 8);
		assertEquals("testExpandValid: Expected expanded range 2 - 6 by factor of 0.2 lower and 0.5 higher to be 1 - 8",expectedExpanded, expanded);
	}
	
	//This method results in an error - should fail instead
	//Error seems to be due to throwing the wrong exception 
	@Test
	public void testExpandNullRange() {
		try {
		Range expanded = Range.expand(null, 0.2, 0.5);
		fail("Expected InvalidParameterException to be thrown");
		}catch(Exception ex) {
			assertEquals("testExpandNullRange: Calling expand with a null range to expand throws InvalidParameterException", InvalidParameterException.class,ex.getClass());
		}
		
	}
	
	//This method results in an error - should fail instead
	//Error seems to be due to throwing the wrong exception
	@Test
	public void testExpandUninitialisedRange() {
		Range rangeToTest = null;
		try {
			Range.expand(rangeToTest, 0.2, 0.5);
			//fail("Expected InvalidParameterException to be thrown");
			} catch(Exception ex) {
				System.out.println(ex.getClass());
				assertEquals("Should be invalid paramater exception", InvalidParameterException.class, ex.getClass());
	}
}
	@Test
	public void testLowerGreaterThanUpperInRangeConstructor() {
		try {
		Range rangeToTest = new Range(15,2);
		} catch (Exception e) {
			assertEquals("Range (double, double) require lower <= upper", IllegalArgumentException.class, e.getClass());
		}
	}
	@Test
	public void testGreaterThanMaxRangeValueInConstrainMethod() {
		Range rangeToTest = new Range(4,12);
		assertEquals("This should return the closest value, in this case 12.0", 12.0, rangeToTest.constrain(13));
	}
	@Test
	public void testLowerThanMinRangeValueInConstrainMethod() {
		Range rangeToTest = new Range(28,95);
		assertEquals("This should return the closest value, in this case 28.0", 28.0, rangeToTest.constrain(2));
	}
	@Test
	public void testRange1NullForCombineMethod() {
		Range range1 = null;
		Range range2 = new Range(20,30);
		assertEquals("This should return range2", range2, Range.combine(range1, range2));
	}
	@Test
	public void testRange2NullForCombineMethod() {
		Range range1 = new Range(12,45);
		Range range2 = null;
		assertEquals("This should return range1", range2, Range.combine(range1, range2));
	}
	@Test
	public void testValidPositiveRangesUsedInCombineMethod() {
		Range range1 = new Range(30,45);
		Range range2 = new Range(1,15);
		Range expectedNewRange = new Range(1,45);
		assertEquals("This should return a new range with values between 1 and 45", expectedNewRange, Range.combine(range1, range2));
	}
	@Test
	public void testShiftMethodInRange() {
		Range expectedNewRange = new Range(4,14);
		assertEquals("The new range should return values between 4 and 14", expectedNewRange, Range.shift(rangeObjectUnderTest, 4));
	}
	@Test
	public void testShiftNoZeroCrossing() {
		Range initialRange = new Range(4,10);
		Range expectedRange = new Range(0, 2);
		
		assertEquals("When shifting a range of 4 - 10 -8 places right with a restriction of not crossing 0, expecting 0-2 as the new range", expectedRange, Range.shift(initialRange, -8, false));
	}
	@Test
	public void testShiftMethodNegative() {
		Range expectedNewRange = new Range(-4,6);
		assertEquals("The new range should return values between -4 and 6", expectedNewRange, Range.shift(rangeObjectUnderTest, -4, true));
	}
	@Test
	public void testExpandToIncludeLowerValid() {
		Range expectedRange = new Range(-3, 10);
		assertEquals("New range should be -3 - 10", expectedRange, Range.expandToInclude(rangeObjectUnderTest, -3));
	}
	@Test
	public void testExpandToIncludeHigherValid() {
		Range expectedRange = new Range(0, 15);
		assertEquals("New range should be 0 - 15", expectedRange, Range.expandToInclude(rangeObjectUnderTest, 15));
	}
	@Test
	public void testExpandToIncludeNullRange() {
		Range expectedRange = new Range(15, 15);
		assertEquals("New range should be 0 - 15", expectedRange, Range.expandToInclude(uninitialisedRange, 15));
	}
	@Test
	public void testExpandToIncludeNullExpansionValue() {
		Range expectedRange = new Range(0, 10);
		assertEquals("New range should be 0 - 15", expectedRange, Range.expandToInclude(rangeObjectUnderTest, uninitialisedDouble));
	}
	@Test
	public void testEqualityWithSameRange() {
		Range newRange = new Range(0,10);
		assertTrue("rangeObjectUnderTest and newRange should be equal", rangeObjectUnderTest.equals(newRange));
	}
	@Test
	public void testEqualityWithDifferentLowerRange() {
		Range newRange = new Range(-12,10);
		assertFalse("rangeObjectUnderTest and newRange should be equal", rangeObjectUnderTest.equals(newRange));
	}
	@Test
	public void testEqualityWithDifferentUpperRange() {
		Range newRange = new Range(0,13);
		assertFalse("rangeObjectUnderTest and newRange should not be equal", rangeObjectUnderTest.equals(newRange));
	}
	@Test
	public void testEqualityWithDifferentRange() {
		Range newRange = new Range(4,20);
		assertFalse("rangeObjectUnderTest and newRange should not be equal", rangeObjectUnderTest.equals(newRange));
	}
	@Test
	public void testEqualityWithDifferentObject() {
		Object compare = new Object();
		assertFalse("rangeObjectUnderTest and newRange should not be equal", rangeObjectUnderTest.equals(compare));
	}
	@Test
	public void testToString() {
		String expectedString = "Range[0,10]";
		assertEquals("rangeObjectUnderTest.toString() should be Range[0,10]",expectedString, rangeObjectUnderTest.toString());
	}
}
