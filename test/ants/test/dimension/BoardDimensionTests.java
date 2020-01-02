package ants.test.dimension;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import ants.board.GameBoard;
import ants.board.cells.Coordinates;
import ants.board.dimension.BoardDimension;
import ants.board.dimension.GameObjectPositions;
import ants.random.RandomWrapper;
import ants.runner.GameConfiguration;
import ants.test.utils.GameBoardBuilder;


public class BoardDimensionTests {
	private RandomWrapper randomWrapperMock;

	@Before 
	public void resetRandomWrapperMock() {
		randomWrapperMock = mock(RandomWrapper.class);
	}
	
	@Test
	public void testThatABoardDimensionCanBeCreatedFromAnXandYDimension() {
		assertThat(BoardDimension.of(3,3), notNullValue());
	}
	
	@Test
	public void testThatTwoDimensionsAreNotEqualWhenTheirXandYDimIsNotTheSame() {
		assertThat(BoardDimension.of(3, 4), not(equalTo(BoardDimension.of(4, 4))));
	}
	
	@Test
	public void testThatTwoDimensionsAreEqualWhenTheirXandYDimIsTheSame() {
		assertThat(BoardDimension.of(3, 4), equalTo(BoardDimension.of(3, 4)));
	}
	
	@Test
	public void testThatTheDimensionCanCreateRandomizedPositionsForTheAntHillAndTheFoodCellsOnTheGameBoard() {
		// the first random position picked is the ant hill
		// then the food cell will be placed around it
		// doubled positions must be skipped
		when(randomWrapperMock.randIntIn(eq(1), eq(3*3))).thenReturn(5)
														 .thenReturn(3)
														 .thenReturn(3)
														 .thenReturn(7)
														 .thenReturn(5)
														 .thenReturn(1);
		GameObjectPositions positions = BoardDimension.of(3, 3)
													  .with(randomWrapperMock)
													  .randomPositionsFor(new GameConfiguration(BoardDimension.of(3, 4),
															  3, 3, 1));
		assertThat(positions.antHillPosition, equalTo(5));
		assertThat(positions.foodCellPositions, contains(3, 7, 1));
		assertThat(positions.foodCellPositions, hasSize(3));
	}
	
	@Test
	public void testComparingTwoBoardDimensionsWhenTheComparedOneIsSmallerInTheXDimension() {
		assertThat(BoardDimension.of(2,3).smallerThan(BoardDimension.of(3, 3)) , is(true));
	}
	
	@Test
	public void testComparingTwoBoardDimensionsWhenTheComparedOneIsSmallerInTheYDimension() {
		assertThat(BoardDimension.of(3,2).smallerThan(BoardDimension.of(3, 3)) , is(true));
	}
	
	@Test
	public void testComparingTwoBoardDimensionsWhenTheComparedOneIsGreater() {
		assertThat(BoardDimension.of(3,3).smallerThan(BoardDimension.of(4, 3)) , is(true));
	}
	
	@Test
	public void testThatABoardDimensionIsSymetricalWhenTheXandYDimensionIsTheSame() {
		assertThat(BoardDimension.of(3, 3).symmetrical(), is(true));
	}
	
	@Test
	public void testThatABoardDimensionIsNotSymetricalWhenTheXandYDimensionAreNotTheSame() {
		assertThat(BoardDimension.of(3, 4).symmetrical(), is(false));
	}
	
	@Test
	public void testThatTheBoardDimensionCanCalculateAllTheCoordinatesLieingInThatDimension() {
		assertThat(BoardDimension.of(3, 3).createCoordinates(),
				contains(new Coordinates(1, 1),
						new Coordinates(2, 1),
						new Coordinates(3, 1),
						new Coordinates(1, 2),
						new Coordinates(2, 2),
						new Coordinates(3, 2),
						new Coordinates(1, 3),
						new Coordinates(2, 3),
						new Coordinates(3, 3)));
	}
	
	@Test
	public void testThatTheBoardDimensionCanPrintOutTheCurrentStateOfAGameBoardGivenInAMapCorrespondingToTheDimensions() {
		GameBoard board = GameBoardBuilder.createEmptyGameBoardIn(BoardDimension.of(3, 4))
							.placeAntHillAt(new Coordinates(2,3), 3)
							.placeFoodCellAt(new Coordinates(2, 2), 10)
							.placeFoodCellAt(new Coordinates(2, 1), 3)
							.build();
		assertThat(BoardDimension.of(3, 3).printStateOf(
				board), equalTo(".3.\n"+
								".10.\n" +
								".A.\n" + 
								"...\n"));
		
	}
	
	@Test
	public void testThatTheBoardDimensionCanPrintOutTheCurrentStateOfAGameBoardGivenInAMapCorrespondingToTheDimensionsWhenAnAntIsStandingOnTheBoard() {
		GameBoard board = GameBoardBuilder.createEmptyGameBoardIn(BoardDimension.of(3, 4))
							.placeAntHillAt(new Coordinates(2,3), 3)
							.placeFoodCellAt(new Coordinates(2, 2), 10)
							.placeFoodCellAt(new Coordinates(2, 1), 3)
							.placeAnt(new Coordinates(1,1))
							.build();
		assertThat(BoardDimension.of(3, 3).printStateOf(board), 
				equalTo("*3.\n" +
						".10.\n" +
						".A.\n" +
						"...\n"));
	}
	
	@Test
	public void testThatTheAntIsPrintedWhenItStandsOnAFoodHill() {
		GameBoard board = GameBoardBuilder.createEmptyGameBoardIn(BoardDimension.of(3, 4))
							.placeAntHillAt(new Coordinates(2,3), 3)
							.placeFoodCellAt(new Coordinates(2, 2), 10)
							.placeFoodCellAt(new Coordinates(2, 1), 3)
							.placeAnt(new Coordinates(2,1))
							.build();
		assertThat(BoardDimension.of(3, 3).printStateOf(board), 
				equalTo(".*.\n" +
						".10.\n" +
						".A.\n" +
						"...\n"));
	}
	
	@Test
	public void testThatTheAntHillIsPrintedWhenAnAntStandsOnTheHill() {
		GameBoard board = GameBoardBuilder.createEmptyGameBoardIn(BoardDimension.of(3, 4))
							.placeAntHillAt(new Coordinates(2,3), 3)
							.placeFoodCellAt(new Coordinates(2, 2), 10)
							.placeFoodCellAt(new Coordinates(2, 1), 3)
							.placeAnt(new Coordinates(2,3))
							.build();
		assertThat(BoardDimension.of(3, 3).printStateOf(board), 
				equalTo(".3.\n" +
						".10.\n" +
						".A.\n" +
						"...\n"));
	}

}
