package ants.test.board;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import ants.board.GameBoardFactory;
import ants.board.cells.Coordinates;
import ants.board.dimension.BoardDimension;
import ants.random.RandomWrapper;
import ants.runner.GameConfiguration;
import ants.test.utils.GameBoardBuilder;

public class GameBoardFactoryTests {
	@Test
	public void testThatTheGameBoardHasExcatlyOneAntHillPlacedSomewhereRandomlyAlsoAssertThatExactlyThreeFoodCellsAreThere() {	
		RandomWrapper randomFoodCellCounts = mock(RandomWrapper.class);
		// returns food count 3, 2, 1
		when(randomFoodCellCounts.randIntIn(1, 3)).thenReturn(3,2,1);
		
		RandomWrapper randomCellPositions = mock(RandomWrapper.class);
		// ant hill position is 2,2
		// food cell positins are 3,2; 1,1; 3,4
		when(randomCellPositions.randIntIn(eq(1), eq(3*4))).thenReturn(5)
											   .thenReturn(6)
											   .thenReturn(1)
											   .thenReturn(12);
		
		BoardDimension boardDimension = BoardDimension.of(3, 4)
													  .with(randomCellPositions);
		GameConfiguration gameConfiguration = new GameConfiguration(boardDimension, 3, 3, 2)
													  .with(randomFoodCellCounts);
		assertThat(GameBoardFactory.createFor(gameConfiguration), 
				equalTo(GameBoardBuilder.createEmptyGameBoardIn(boardDimension)
						.placeAntHillAt(new Coordinates(2,2), 2)
						.placeFoodCellAt(new Coordinates(1,1), 3)
						.placeFoodCellAt(new Coordinates(3,2), 2)
						.placeFoodCellAt(new Coordinates(3,4), 1)
						.build()));
	}
}
