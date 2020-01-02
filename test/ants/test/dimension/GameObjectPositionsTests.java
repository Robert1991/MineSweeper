package ants.test.dimension;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import ants.board.dimension.GameObjectPositions;

public class GameObjectPositionsTests {
	@Test
	public void testThatAPositionOnTheBoardIsNotAFoodCellPositionWhenTheGivenIndexDoesNotLieInTheFoodCellPositions() {
		Integer[] positions = {1, 5 ,8};
		assertThat(new GameObjectPositions(10, Arrays.asList(positions)).isFoodCellPosition(7), is(false));
	}
	
	@Test
	public void testThatAPositionOnTheBoardIsAFoodCellPositionWhenTheGivenIndexLiesInTheFoodCellPositions() {
		Integer[] positions = {1, 5 ,8};
		assertThat(new GameObjectPositions(10, Arrays.asList(positions)).isFoodCellPosition(5), is(true));
	}
	
	@Test
	public void testThatAPositionIsTheAntHillPositionWhenTheAntHillPositionIsEqualToTheGivenOne() {
		assertThat(new GameObjectPositions(10, new ArrayList<Integer>()).isAntHillPosition(10), is(true));
	}
	
	@Test
	public void testThatAPositionIsNotTheAntHillPositionWhenTheAntHillPositionIsDifferentFromTheGivenOne() {
		assertThat(new GameObjectPositions(10, new ArrayList<Integer>()).isAntHillPosition(4), is(false));
	}
}
