package ants.test.board;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ants.ants.Ant;
import ants.ants.AntMovingDirection;
import ants.board.GameBoard;
import ants.board.GameBoardFactory;
import ants.board.cells.Coordinates;
import ants.board.cells.FoodCell;
import ants.board.dimension.BoardDimension;
import ants.random.RandomWrapper;
import ants.runner.GameConfiguration;
import ants.test.utils.GameBoardBuilder;

public class GameBoardTests {
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	private GameBoard gameBoard;
	
	@Before
	public void removeStaticMocksIfDefined() {
		AntMovingDirection.randomWrapper = new RandomWrapper();
	}
	
	@After
	public void removeStaticMocks() {
		AntMovingDirection.randomWrapper = new RandomWrapper();
		gameBoard = null;
	}
	
	@Test
	public void testCreatingTheGameFieldWithTheAValidXandYDimensionAndAValidGameConfiguration() {
		assertThat(new GameBoard(BoardDimension.of(4, 3)), notNullValue());
	}
	
	@Test
	public void testThatAGameFieldCanNotBeCreatedWithSymmetricalDimensionsBecauseAntsCanGetLostInTheHausOfTheNikolausThen() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("board dimension can not be symmetrical for ant simulation");
		gameBoard = new GameBoard(BoardDimension.of(3, 3));
	}
	
	@Test
	public void testThatAGameFieldCanNotBeCreatedWithAXDimensionSmallerThan3BecauseEachFieldNeedsAtLeast8Neighbours() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("the game field needs at least a dimension of 3x4 or 4x3");
		gameBoard = new GameBoard(BoardDimension.of(2, 4));
	}
	
	@Test
	public void testThatAGameFieldCanNotBeCreatedWithAYDimensionSmallerThan3BecauseEachFieldNeedsAtLeast8Neighbours() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("the game field needs at least a dimension of 3x4 or 4x3");
		gameBoard = new GameBoard(BoardDimension.of(4, 2));
	}
	
	@Test
	public void testThatAGameBoardIsEqualToAnotherGameBoardWhenTheyHaveTheSameDimensionAndCellPositions() {
		assertThat(GameBoardBuilder.createEmptyGameBoardIn(new BoardDimension(3, 4))
								   .placeAntHillAt(new Coordinates(3, 4),  2)
								   .placeFoodCellAt(new Coordinates(1, 1), 3)
								   .build(), 
				equalTo(GameBoardBuilder.createEmptyGameBoardIn(new BoardDimension(3, 4))
										.placeAntHillAt(new Coordinates(3, 4),  2)
										.placeFoodCellAt(new Coordinates(1, 1), 3)
										.build()));
	}
	
	@Test
	public void testThatAGameBoardIsEqualToAnotherGameBoardWhenTheyHaveTheSameDimensionAndCellPositionsAndAnts() {
		assertThat(GameBoardBuilder.createEmptyGameBoardIn(new BoardDimension(3, 4))
								   .placeAntHillAt(new Coordinates(3, 4),  2)
								   .placeFoodCellAt(new Coordinates(1, 1), 3)
								   .placeAnt(new Coordinates(2,2))
								   .build(), 
				equalTo(GameBoardBuilder.createEmptyGameBoardIn(new BoardDimension(3, 4))
										.placeAntHillAt(new Coordinates(3, 4),  2)
										.placeFoodCellAt(new Coordinates(1, 1), 3)
										.placeAnt(new Coordinates(2,2))
										.build()));
	}
	
	@Test
	public void testThatAGameBoardIsNotEqualToAnotherGameBoardWhenTheyHaveDifferentDimensions() {
		assertThat(GameBoardBuilder.createEmptyGameBoardIn(new BoardDimension(3, 5))
								   .placeAntHillAt(new Coordinates(3, 4),  2)
								   .placeFoodCellAt(new Coordinates(1, 1), 3)
								   .build(), 
				not(equalTo(GameBoardBuilder.createEmptyGameBoardIn(new BoardDimension(3, 4))
										.placeAntHillAt(new Coordinates(3, 4),  2)
										.placeFoodCellAt(new Coordinates(1, 1), 3)
										.build())));
	}
	
	@Test
	public void testThatAGameBoardIsNotEqualToAnotherGameBoardWhenTheAntHillPositionDiffers() {
		assertThat(GameBoardBuilder.createEmptyGameBoardIn(new BoardDimension(3, 5))
								   .placeAntHillAt(new Coordinates(3, 5),  2)
								   .placeFoodCellAt(new Coordinates(1, 1), 3)
								   .build(), 
				not(equalTo(GameBoardBuilder.createEmptyGameBoardIn(new BoardDimension(3, 5))
										.placeAntHillAt(new Coordinates(3, 3),  2)
										.placeFoodCellAt(new Coordinates(1, 1), 3)
										.build())));
	}
	
	@Test
	public void testThatAGameBoardIsNotEqualToAnotherGameBoardWhenTheFoodCellCountDiffers() {
		assertThat(GameBoardBuilder.createEmptyGameBoardIn(new BoardDimension(3, 4))
								   .placeAntHillAt(new Coordinates(3, 5),  2)
								   .placeFoodCellAt(new Coordinates(1, 1), 3)
								   .placeFoodCellAt(new Coordinates(1, 2), 3)
								   .build(), 
				not(equalTo(GameBoardBuilder.createEmptyGameBoardIn(new BoardDimension(3, 4))
										.placeAntHillAt(new Coordinates(3, 5),  2)
										.placeFoodCellAt(new Coordinates(1, 1), 3)
										.build())));
	}
	
	@Test
	public void testThatAGameBoardIsNotEqualToAnotherGameBoardWhenTheFoodCellPositionsDiffer() {
		assertThat(GameBoardBuilder.createEmptyGameBoardIn(new BoardDimension(3, 4))
								   .placeAntHillAt(new Coordinates(3, 5),  2)
								   .placeFoodCellAt(new Coordinates(1, 2), 3)
								   .build(), 
				not(equalTo(GameBoardBuilder.createEmptyGameBoardIn(new BoardDimension(3, 4))
										.placeAntHillAt(new Coordinates(3, 5),  2)
										.placeFoodCellAt(new Coordinates(1, 1), 3)
										.build())));
	}
	
	@Test
	public void testThatAGameBoardIsNotEqualToAnotherGameBoardWhenTheAntCountIsNotEqual() {
		assertThat(GameBoardBuilder.createEmptyGameBoardIn(new BoardDimension(3, 4))
								   .placeAntHillAt(new Coordinates(3, 5),  2)
								   .placeFoodCellAt(new Coordinates(1, 2), 3)
								   .placeAnt(new Coordinates(3,3))
								   .build(), 
				not(equalTo(GameBoardBuilder.createEmptyGameBoardIn(new BoardDimension(3, 4))
										.placeAntHillAt(new Coordinates(3, 5),  2)
										.placeFoodCellAt(new Coordinates(1, 2), 3)
										.build())));
	}
	
	@Test
	public void testThatAGameBoardIsNotEqualToAnotherGameBoardWhenTheAntPositionsAreNotEqual() {
		assertThat(GameBoardBuilder.createEmptyGameBoardIn(new BoardDimension(3, 4))
								   .placeAntHillAt(new Coordinates(3, 5),  2)
								   .placeFoodCellAt(new Coordinates(1, 2), 3)
								   .placeAnt(new Coordinates(3,3))
								   .build(), 
				not(equalTo(GameBoardBuilder.createEmptyGameBoardIn(new BoardDimension(3, 4))
										.placeAntHillAt(new Coordinates(3, 5),  2)
										.placeFoodCellAt(new Coordinates(1, 2), 3)
										.placeAnt(new Coordinates(3,4))
										.build())));
	}
	
	@Test
	public void verifyThatAnAntGetsReleasedFromTheAntHillInTheFirstIterationOfTheGameAlsoTestThatThePositionOfThatAntIsTheAntHill() {
		gameBoard = GameBoardBuilder.createEmptyGameBoardIn(BoardDimension.of(3,5))
				  .placeAntHillAt(new Coordinates(2,3), 3)
				  .placeFoodCellAt(new Coordinates(1,1), 2)
				  .build();
		gameBoard.iterate();
		assertThatTheAntCountIs(1);
		assertThatAntHasPositionOnBoard(0, new Coordinates(2, 3));
	}
	
	@Test
	public void testThatTheAntHillDoesNotReleaseAnOtherAntWhenThereIsNoMoreFoodToCollectOnTheBoard() {
		Ant antOnBoard = new Ant(new Coordinates(1,1));
		antOnBoard.move();
		antOnBoard.takeFoodUnitFrom(new FoodCell(1));
		gameBoard = GameBoardBuilder.createEmptyGameBoardIn(BoardDimension.of(3,5))
										  .placeAntHillAt(new Coordinates(2,3), 3)
										  .placeAnt(antOnBoard)
										  .build();
		gameBoard.iterate();
		assertThatTheAntCountIs(1);
	}
	
	@Test
	public void testThatTheAntHillReleasesTheNextAntWhenThereIsStillFoodLeftButNotEnoughAntsToCarryItBecauseOneOfThemIsAlreadyPacked() {
		Ant antPackedWithFood = new Ant(new Coordinates(1,1));
		antPackedWithFood.move();
		antPackedWithFood.takeFoodUnitFrom(new FoodCell(1));
		gameBoard = GameBoardBuilder.createEmptyGameBoardIn(BoardDimension.of(3,5))
										  .placeAntHillAt(new Coordinates(2,3), 3)
										  .placeFoodCellAt(new Coordinates(5, 3), 2)
										  .placeAnt(antPackedWithFood)
										  .placeAnt(new Coordinates(1, 1))
										  .build();
		gameBoard.iterate();
		assertThatTheAntCountIs(3);
	}
	
	@Test
	public void testThatTheAntHillDoesNotReleaseAnotherAntWhenThereIsStillFoodLeftOnTheBoardButEnoughAntsToCollectIt() {
		gameBoard = GameBoardBuilder.createEmptyGameBoardIn(BoardDimension.of(3,5))
				  					.placeAntHillAt(new Coordinates(2,3), 3)
				  					.placeAnt(new Coordinates(1,1))
				  					.placeAnt(new Coordinates(1,2))
				  					.placeFoodCellAt(new Coordinates(5, 3), 2)
				  					.build();
		gameBoard.iterate();
		gameBoard.iterate();
		assertThatTheAntCountIs(2);
	}
	
	@Test
	public void testThatAnAntMovesNorthFromTheGivenAntHillPositionAfterItPoppedOutOfTheHillInTheFirstIteration() {
		gameBoard = GameBoardBuilder.createEmptyGameBoardIn(BoardDimension.of(3,4))
										  .placeAntHillAt(new Coordinates(2,2), 3)
										  .placeFoodCellAt(new Coordinates(1, 1), 3)
										  .build();
		letAntMoveInDirection(AntMovingDirection.N);
		gameBoard.iterate();
		assertThatAntHasPositionOnBoard(0, new Coordinates(2,2));
		gameBoard.iterate();
		assertThatAntHasPositionOnBoard(0, new Coordinates(2,1));
	}
	
	@Test
	public void testThatTheAntTurnsToTheNextRandomDirectionWhenItHitsTheBoarderWhileMovingInItsFirstDefinedDirection() {
		gameBoard = GameBoardBuilder.createEmptyGameBoardIn(BoardDimension.of(3,4))
				  .placeAntHillAt(new Coordinates(3,3), 3)
				  .placeFoodCellAt(new Coordinates(1, 1), 3)
				  .build();
		letAntMoveInDirection(AntMovingDirection.E, AntMovingDirection.W);
		// ant pops up
		gameBoard.iterate();
		// ant turns
		gameBoard.iterate();
		// ant moves west to (2,3)
		gameBoard.iterate();
		assertThatAntHasPositionOnBoard(0, new Coordinates(2,3));
	}
	
	@Test
	public void testThatTheAntHillReleasesTheNextAntAsSoonAsTheFirstAntMovedFromTheAntHill() {
		gameBoard = GameBoardBuilder.createEmptyGameBoardIn(BoardDimension.of(3,4))
				  .placeAntHillAt(new Coordinates(3,3), 3)
				  .placeFoodCellAt(new Coordinates(1, 1), 10)
				  .build();
		letAntMoveInDirection(AntMovingDirection.N);
		// first ant pops up
		gameBoard.iterate();
		// first ant moves from ant hill and second ant pops out of the hill
		gameBoard.iterate();
		assertThatAntHasPositionOnBoard(0, new Coordinates(3,2));
		assertThatAntHasPositionOnBoard(1, new Coordinates(3,3));
		assertThatTheAntCountIs(2);
	}
	
	@Test
	public void testThatTheAntHillReleasesNoMoreAntWhenThereIsOneFoodUnitLeftAndAlreadyAnAntOnTheBoardWhichDidNotCollectAnyFoodYet() {
		gameBoard = GameBoardBuilder.createEmptyGameBoardIn(BoardDimension.of(3,4))
				  .placeAntHillAt(new Coordinates(3,3), 3)				  
				  .placeAnt(new Coordinates(3,1))
				  .placeFoodCellAt(new Coordinates(1, 1), 1)
				  .build();
		// first ant pops up
		gameBoard.iterate();
		gameBoard.iterate();
		assertThatTheAntCountIs(1);
	}
	
	@Test
	public void testThatTheAntTurnsWhenItsWayIsBlockedByAnOtherAntStandingOnTheGameFieldWhereTheSecondAntWantsToMoveTo() {
		gameBoard = GameBoardBuilder.createEmptyGameBoardIn(BoardDimension.of(3,4))
				  .placeAntHillAt(new Coordinates(1,1), 3)
				  .placeAnt(new Coordinates(2, 2), AntMovingDirection.E)
				  .placeAnt(new Coordinates(3, 3), AntMovingDirection.N)
				  .build();
		letAntMoveInDirection(AntMovingDirection.W);
		// first ant moves to 
		gameBoard.iterate();
		// first ant moves to 3,3 and second ant has to turn
		assertThatAntHasPositionOnBoard(0, new Coordinates(3,2));
		assertThatAntHasPositionOnBoard(1, new Coordinates(3,3));
		// second ant now moves west while first ant turns because it hit the border
		gameBoard.iterate();
		assertThatAntHasPositionOnBoard(0, new Coordinates(3,2));
		assertThatAntHasPositionOnBoard(1, new Coordinates(2,3));
	}
	
	@Test
	public void testThatAnAntConsumesOneUnitOfFoodWhenItFindsAFoodCellAndThenReturnsBackOnTheSamePathToTheAntHill() {
		gameBoard = GameBoardBuilder.createEmptyGameBoardIn(BoardDimension.of(3,4))
				  .placeAntHillAt(new Coordinates(3,3), 1)
				  .placeFoodCellAt(new Coordinates(2, 1), 1)
				  .build();
		letAntMoveInDirection(AntMovingDirection.N,
							  AntMovingDirection.W);
		int foodUnitsInBoard = gameBoard.foodUnitsLeft();
		// ant pops out of the ant hill 
		gameBoard.iterate();
		assertThatAntHasPositionOnBoard(0, new Coordinates(3,3));
		// ant moves 2 steps north
		gameBoard.iterate();
		assertThatAntHasPositionOnBoard(0, new Coordinates(3,2));
		gameBoard.iterate();
		assertThatAntHasPositionOnBoard(0, new Coordinates(3,1));
		// ant hits the border and changes its direction west
		gameBoard.iterate();
		assertThatAntHasPositionOnBoard(0, new Coordinates(3,1));
		gameBoard.iterate();		
		// ant found food
		assertThatAntHasPositionOnBoard(0, new Coordinates(2,1));
		assertThatTheRemainingFoodCountOnTheBoardIs(foodUnitsInBoard -1);
		// ant returns back home, where it arrives after 4 iterations
		// endless iteration after here! 
		gameBoard.iterate();
		assertThatAntHasPositionOnBoard(0, new Coordinates(3,1));
		gameBoard.iterate();
		assertThatAntHasPositionOnBoard(0, new Coordinates(3,2));
		gameBoard.iterate();
		assertThatAntHasPositionOnBoard(0, new Coordinates(3,3));
		gameBoard.iterate();
		// the ant disappeard into the ant hill
		assertThatTheAntCountIs(0);
	}
	
	@Test
	public void testThatAnFoodCellDisappearsFromTheBoardAfterAnAntConsumedTheLastFoodUnitOfIt() {
		gameBoard = GameBoardBuilder.createEmptyGameBoardIn(BoardDimension.of(3,4))
				  					.placeAntHillAt(new Coordinates(3,3), 0)
				  					.placeFoodCellAt(new Coordinates(2, 1), 1)
				  					.placeAnt(new Coordinates(2,3), AntMovingDirection.N)
				  					.build();
		gameBoard.iterate();
		assertThatAntHasPositionOnBoard(0, new Coordinates(2,2));
		gameBoard.iterate();
		assertThatAntHasPositionOnBoard(0, new Coordinates(2,1));
		assertThatTheRemainingFoodCountOnTheBoardIs(0);
		gameBoard.iterate();
		assertThatAntHasPositionOnBoard(0, new Coordinates(2,2));
		gameBoard.iterate();
		assertThat(gameBoard, equalTo(GameBoardBuilder.createEmptyGameBoardIn(BoardDimension.of(3,4))
					.placeAntHillAt(new Coordinates(3,3), 0)
					.placeAnt(new Coordinates(2,3))
					.build()));
	}
	
	@Test
	public void testThatTheCurrentFoodInTransportationCountEqualsTheCountOfAntsCarryingFood() {
		FoodCell food = new FoodCell(2);
		Ant antHeadingNorth = new Ant(new Coordinates(1, 1), new Coordinates(1, 2), new Coordinates(1, 3));
		antHeadingNorth.takeFoodUnitFrom(food);
		Ant antHeadingSouth = new Ant(new Coordinates(1, 4), new Coordinates(1, 3), new Coordinates(1, 2));
		antHeadingSouth.takeFoodUnitFrom(food);
		assertThat(GameBoardBuilder.createEmptyGameBoardIn(BoardDimension.of(5,7))
				  .placeAntHillAt(new Coordinates(5,7), 0)
				  .placeFoodCellAt(new Coordinates(5,5), 1)
				  .placeAnt(antHeadingNorth)
				  .placeAnt(antHeadingSouth)
				  .placeAnt(new Ant(new Coordinates(1,1)))
				  .build()
				  .foodUnitsInTransportation(), equalTo(2));
	}
		
	@Test
	public void testThatTwoAntsHeadingBackToTheAntHillWillSwapTheirPostionsIfBothAntsCollide() {
		FoodCell food = new FoodCell(2);
		Ant antHeadingNorth = new Ant(new Coordinates(1, 1), new Coordinates(1, 2), new Coordinates(1, 3));
		antHeadingNorth.takeFoodUnitFrom(food);
		Ant antHeadingSouth = new Ant(new Coordinates(1, 4), new Coordinates(1, 3), new Coordinates(1, 2));
		antHeadingSouth.takeFoodUnitFrom(food);
		gameBoard = GameBoardBuilder.createEmptyGameBoardIn(BoardDimension.of(5,7))
				  .placeAntHillAt(new Coordinates(5,7), 0)
				  .placeFoodCellAt(new Coordinates(5,5), 1)
				  .placeAnt(antHeadingNorth)
				  .placeAnt(antHeadingSouth)
				  .build();
		gameBoard.iterate();
		assertThatAntHasPositionOnBoard(0, new Coordinates(1,2));
		assertThatAntHasPositionOnBoard(1, new Coordinates(1,3));
		gameBoard.iterate();
		assertThatAntHasPositionOnBoard(0, new Coordinates(1,1));
		assertThatAntHasPositionOnBoard(1, new Coordinates(1,4));
	}
		
	@Test
	public void testCalculatingTheRemainingFoodCountOnTheBoardAndCheckThatItIsTheSumOffAllFoodUnitsLeftInTheFoodCells() {
		assertThat(GameBoardBuilder.createEmptyGameBoardIn(BoardDimension.of(3, 4))
								.placeAntHillAt(new Coordinates(3, 3), 1)
								.placeFoodCellAt(new Coordinates(1, 1), 15)
								.placeFoodCellAt(new Coordinates(2, 1), 3)
								.placeFoodCellAt(new Coordinates(3, 1), 6)
								.build()
								.foodUnitsLeft(), equalTo(15 + 3 + 6));
	}

	@Test
	public void testThatThereIsStillFoodToCollectWhenThereAreSomeFoodCellsLeftWithSomeFoodOnTheBoard() {
		assertThat(GameBoardBuilder.createEmptyGameBoardIn(BoardDimension.of(3, 4))
						.placeAntHillAt(new Coordinates(3,3), 1)
						.placeFoodCellAt(new Coordinates(1, 1), 15)
						.build()
						.stillFoodToCollect(), is(true));
	}
	
	@Test
	public void testThatThereIsStillFoodToCollectWhenThereThereAreNoFoodCellsOnTheBoardAnyMoreButThereIsStillAnAntThatHasNotReturnedToTheHill() {
		assertThat(GameBoardBuilder.createEmptyGameBoardIn(BoardDimension.of(3, 4))
						.placeAntHillAt(new Coordinates(3,3), 1)
						.placeAnt(new Coordinates(2,2))
						.build()
						.stillFoodToCollect(), is(true));
	}
	
	@Test
	public void testThatThereIsNoFoodToCollectAnyMoreWhenAllAntsHaveReturnedToTheHillAndThereIsNoFoodCellAnymore() {
		assertThat(GameBoardBuilder.createEmptyGameBoardIn(BoardDimension.of(3, 4))
						.placeAntHillAt(new Coordinates(3, 3), 1)
						.build()
						.stillFoodToCollect(), is(false));
	}
	
	@Test
//	@Ignore
	public void foo() throws InterruptedException {
		GameBoard board = GameBoardFactory.createFor(new GameConfiguration(BoardDimension.of(10, 15), 25,7,9));
		while(board.stillFoodToCollect()) {			
			//System.out.println("--------");
			Thread.sleep(250);
			board.iterate();
		}
	}
	
	private void assertThatTheAntCountIs(int count) {
		assertThat(gameBoard.antsInGame(), hasSize(count));
	}
	
	private void assertThatAntHasPositionOnBoard(int antNumber, Coordinates coordinates) {
		assertThat(gameBoard.antsInGame().get(antNumber).currentPosition(), equalTo(coordinates));
	}

	private void assertThatTheRemainingFoodCountOnTheBoardIs(int foodCount) {
		assertThat(gameBoard.foodUnitsLeft(), equalTo(foodCount));
	}
	
	private void letAntMoveInDirection(AntMovingDirection direction) {
		RandomWrapper random = mock(RandomWrapper.class);
		when(random.randIntIn(eq(1), eq(8))).thenReturn(direction.number);
		AntMovingDirection.randomWrapper = random;
	}
	
	private void letAntMoveInDirection(AntMovingDirection direction, AntMovingDirection... antMovingDirections) {
		int[] directions = Arrays.asList(antMovingDirections).stream().mapToInt(dir -> dir.number)
																	  .toArray();
		RandomWrapper random = mock(RandomWrapper.class);
		when(random.randIntIn(eq(1), eq(8))).thenReturn(direction.number, Arrays.stream(directions).boxed().toArray(Integer[]::new));
		AntMovingDirection.randomWrapper = random;
	}
}
