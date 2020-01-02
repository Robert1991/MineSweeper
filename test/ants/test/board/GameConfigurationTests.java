package ants.test.board;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ants.board.cells.Coordinates;
import ants.board.cells.FoodCell;
import ants.board.cells.GameCell;
import ants.board.dimension.BoardDimension;
import ants.runner.GameConfiguration;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class GameConfigurationTests {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testThatTheGameConfigurationNeedsAtLeastOneFoodUnitPerFieldToBeInitialized() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("the minimum food count per cell is 1!");
		new GameConfiguration(new BoardDimension(3, 4), 0, 3, 1);
	}
	
	@Test
	public void testThatTheGameConfigurationNeedsAtLeastOneFoodCell() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("the minimum food cell count is 1!");
		new GameConfiguration(new BoardDimension(3, 4), 1, 0, 1);
	}
	
	@Test
	public void testThatTheGameConfigurationNeedsAtLeastOneAnt() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("there has to be at least one ant in that hill!");
		new GameConfiguration(new BoardDimension(3, 4), 1, 1, 0);
	}
	
	@Test
	public void testThatTheGameNeedsAnAsymmetricalBoardDimension() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("board dimension can not be symmetrical for ant simulation");
		new GameConfiguration(new BoardDimension(3, 3), 1, 1, 1);
	}
	
	@Test
	public void testThatTheGameConfigurationCreatesAnAntHillCellWithTheConfiguredAntCount() {
		assertThat(new GameConfiguration(new BoardDimension(3, 4),10,10, 5).createAntHillAt(new Coordinates(2, 3)), 
				equalTo(GameCell.antHill(5, new Coordinates(2, 3))));
	}
	
	@Test
	public void testThatTheGameConfigurationCreatesAFoodCellWithAFoodCountInTheRangeOfOneToTheMaximumGivenFoodCountInTheGameConfiguration() {
		assertThat(((FoodCell)new GameConfiguration(new BoardDimension(3, 4),10, 5, 5).nextFoodCell())
												  .currentFoodCount(), 
			is(both(greaterThanOrEqualTo(1)).and(lessThanOrEqualTo(10))));
	}
}
