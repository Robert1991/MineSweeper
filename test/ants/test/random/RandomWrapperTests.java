package ants.test.random;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ants.random.RandomWrapper;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Rule;

public class RandomWrapperTests {
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testThatTheRandomWrapperReturnsAnRandomInWithinTheGivenRange() {
		assertThat(new RandomWrapper().randIntIn(1, 5), 
				is(both(greaterThanOrEqualTo(1)).and(lessThanOrEqualTo(5))));
	}
	
	@Test
	public void testThatTheLowerBoundHasToBeSmallerThanTheUpperBoundWhenGeneratingARandomIntInAGivenRange() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("lower bound has to be smaller than upper bound by at least 1");
		new RandomWrapper().randIntIn(6, 3);
	}
	
	@Test
	public void testThatTheLowerBoundHasToBeSmallerThanTheUpperBoundWhenGeneratingARandomIntInAGivenRangeAndBothBoundsAreEqual() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("lower bound has to be smaller than upper bound by at least 1");
		new RandomWrapper().randIntIn(3, 3);
	}
}
