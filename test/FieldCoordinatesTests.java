import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import coordinates.FieldCoordinates;


public class FieldCoordinatesTests {
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testThatItIsNotPossibleToCreateFieldCoordinatesWhenTheXCoordinateIsNegative() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(equalTo("the coordinates can not be negative"));
		FieldCoordinates.createFor(-1, 10);
	}
	
	@Test
	public void testThatItIsNotPossibleToCreateFieldCoordinatesWhenTheYCoordinateIsNegative() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(equalTo("the coordinates can not be negative"));
		FieldCoordinates.createFor(1, -10);
	}
	
	@Test
	public void testThatTwoFieldCoordinatesAreNeighborsWhenTheirDistanceIsSmallerOrEqualThanOne() {
		final NeighborCallBack callback = new NeighborCallBack();
		FieldCoordinates.createFor(5, 5).ifNeighbor(FieldCoordinates.createFor(4, 5), 
				() -> callback.wasCalled = true);
		assertTrue(callback.wasCalled);
	}
	
	@Test
	public void testThatTwoFieldCoordinatesAreNeighborsWhenTheirDistanceIsSmallerOrEqualThanOne2() {
		final NeighborCallBack callback = new NeighborCallBack();
		FieldCoordinates.createFor(5, 5).ifNeighbor(FieldCoordinates.createFor(5, 4), 
				() -> callback.wasCalled = true);
		assertTrue(callback.wasCalled);
	}
	
	@Test
	public void testThatTwoFieldCoordinatesAreNeighborsWhenTheirDistanceIsSmallerOrEqualThanOne3() {
		final NeighborCallBack callback = new NeighborCallBack();
		FieldCoordinates.createFor(5, 5).ifNeighbor(FieldCoordinates.createFor(4, 4), 
				() -> callback.wasCalled = true);
		assertTrue(callback.wasCalled);
	}
	
	@Test
	public void testThatTwoFieldCoordinatesAreNoNeighborsWhenTheyAreEqual() {
		final NeighborCallBack callback = new NeighborCallBack();
		FieldCoordinates.createFor(5, 5).ifNeighbor(FieldCoordinates.createFor(5, 5), 
				() -> callback.wasCalled = true);
		assertFalse(callback.wasCalled);
	}
	class NeighborCallBack {
		public boolean wasCalled = false;
	}
	
	@Test
	public void testThatTwoFieldCoordinatesAreEqualWhenTheirCoordinatesMatch() {
		assertThat(FieldCoordinates.createFor(5, 5), equalTo(FieldCoordinates.createFor(5, 5)));
	}
	
	@Test
	public void testThatTwoFieldCoordinatesAreNotEqualWhenTheirCoordinatesDifferInX() {
		assertThat(FieldCoordinates.createFor(5, 5), not(equalTo(FieldCoordinates.createFor(7, 5))));
	}
	
	@Test
	public void testThatTwoFieldCoordinatesAreNotEqualWhenTheirCoordinatesDifferInY() {
		assertThat(FieldCoordinates.createFor(5, 5), not(equalTo(FieldCoordinates.createFor(5, 7))));
	}
	
	@Test
	public void testThatTheFieldCoordinatesAreNotEqualToNull() {
		assertThat(FieldCoordinates.createFor(5, 5), not(equalTo(null)));
	}
	
	@Test
	public void testThatTheFieldCoordinatesAreNotEqualToAnOtherType() {
		assertThat(FieldCoordinates.createFor(5, 5), not(equalTo("string")));
	}
}
