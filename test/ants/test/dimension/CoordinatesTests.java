package ants.test.dimension;
import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ants.board.cells.Coordinates;

import static org.hamcrest.Matchers.*;

public class CoordinatesTests {
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testThatItIsNotPossibleToShiftToNegativeCoordinates() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Cooridnates have to be positive!");
		new Coordinates(1,1).shiftWith(-3,1);
	}
	
	@Test
	public void testShiftingTheCoordintesToADefinedDirectionWithXAndYOffsetInNorthEastern() {
		assertThat(new Coordinates(2,2).shiftWith(1,1), equalTo(new Coordinates(3,3)));
	}
	
	@Test
	public void testShiftingTheCoordintesToADefinedDirectionWithXAndYOffsetInSouthernDirection() {
		assertThat(new Coordinates(2,2).shiftWith(0,1), equalTo(new Coordinates(2,3)));
	}
	
	@Test
	public void testShiftingTheCoordintesToADefinedDirectionWithXAndYOffsetInEasterDirection() {
		assertThat(new Coordinates(2,2).shiftWith(1,0), equalTo(new Coordinates(3,2)));
	}
	
	@Test
	public void testThatCoordinatesCanOnlyBeInitializedWithPositiveCoordinates() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Cooridnates have to be positive!");
		new Coordinates(1, -1);
	}
	
	@Test
	public void testThatTwoCoordinatesAreNotTheSameWhenTheXDimDiffers() {
		assertThat(new Coordinates(1,2), is(not(equalTo(new Coordinates(2,2)))));
	}
	
	@Test
	public void testThatTwoCoordinatesAreNotTheSameWhenTheYDimDiffers() {
		assertThat(new Coordinates(2,1), is(not(equalTo(new Coordinates(2,2)))));
	}
	
	@Test
	public void testTheToStringRepresentationOfTheCoordinates() {
		assertThat(new Coordinates(1, 1).toString(), equalTo("{x:1, y:1}"));
	}
	
	@Test
	public void testThatTwoEqualCoordinatesAreEqualWhenComparingThem() {
		assertThat(
				new Coordinates(1,1).compareTo(new Coordinates(1,1)), equalTo(0));
	}
	
	@Test
	public void testThatOneCoordinateIsSmallerThanTheOtherWhenItsXDimensionIsSmaller() {
		assertThat(
				new Coordinates(1,1).compareTo(new Coordinates(2,1)), lessThan(0));
	}
	
	@Test
	public void testThatOneCoordinateIsSmallerThanTheOtherWhenItsYDimensionIsSmaller() {
		assertThat(
				new Coordinates(1,1).compareTo(new Coordinates(1,2)), lessThan(0));
	}
	
	@Test
	public void testThatOneCoordinateIsSmallerThanTheOtherWhenItsYAndXDimensionIsSmaller() {
		assertThat(
				new Coordinates(1,1).compareTo(new Coordinates(2,2)), lessThan(0));
	}
	
	@Test
	public void testThatOneCoordinateIsGreaterThanTheOtherWhenItsYAndXDimensionIsGreater() {
		assertThat(
				new Coordinates(2,2).compareTo(new Coordinates(1,1)), greaterThan(0));
	}
	
	@Test
	public void testThatOneCoordinateIsGreaterThanTheOtherWhenItsYAndXDimensionIsGreater2() {
		assertThat(
				new Coordinates(1,2).compareTo(new Coordinates(2,1)), greaterThan(0));
	}
	
	@Test
	public void testThatOneCoordinateIsGreaterThanTheOtherWhenItsYAndXDimensionIsGreater3() {
		assertThat(
				new Coordinates(1,2).compareTo(new Coordinates(1,1)), greaterThan(0));
	}
}
