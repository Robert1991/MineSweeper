package ants.test.ants;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ants.ants.Ant;
import ants.ants.AntMovingDirection;
import ants.board.cells.Coordinates;
import ants.board.cells.FoodCell;
import ants.random.RandomWrapper;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class AntTests {
	private RandomWrapper random;

	@Before
	public void assignRandomWrapperMockToAntMovingDirections() {
		random = mock(RandomWrapper.class);
		when(random.randIntIn(eq(1), eq(8))).thenReturn(1);
		AntMovingDirection.randomWrapper = random;
	}
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@After
	public void removeStaticMocks() {
		AntMovingDirection.randomWrapper = new RandomWrapper();
	}
	
	@Test
	public void testThatAnAntCarryingFoodIsDisplayedAsPlusOnTheBoard() {
		Ant ant = new Ant(new Coordinates(3,3));
		ant.takeFoodUnitFrom(new FoodCell(3));
		assertThat(ant.printToBoard(), equalTo("+"));
	}
	
	@Test
	public void testThatAnAntHeadingBackToTheAntHillCanSwapPositonsWithAnotherAntGoingBack() {
		Ant antHeadingSouth = new Ant(new Coordinates(3,3), new Coordinates(3,2), new Coordinates(3,1));
		antHeadingSouth.takeFoodUnitFrom(new FoodCell(3));
		Ant antHeadingNorth = new Ant(new Coordinates(2,1), new Coordinates(3,1), new Coordinates(3,2));
		antHeadingNorth.takeFoodUnitFrom(new FoodCell(3));
		antHeadingNorth.swapWith(antHeadingSouth);
		assertThat(antHeadingNorth.currentPosition(), equalTo(new Coordinates(3, 1)));
		assertThat(antHeadingNorth.nextStep(), equalTo(new Coordinates(2, 1)));
		assertThat(antHeadingSouth.currentPosition(), equalTo(new Coordinates(3, 2)));
		assertThat(antHeadingSouth.nextStep(), equalTo(new Coordinates(3, 3)));
	}
	
	@Test
	public void testThatAnAntHeadingBackToTheAntHillCanSwapPositonsWithAnotherAntGoingBackWhenBothHaveTwoElementsOfThePathLeft() {
		Ant antHeadingSouth = new Ant(new Coordinates(3,2), new Coordinates(3,1));
		antHeadingSouth.takeFoodUnitFrom(new FoodCell(3));
		Ant antHeadingNorth = new Ant(new Coordinates(3,1), new Coordinates(3,2));
		antHeadingNorth.takeFoodUnitFrom(new FoodCell(3));
		antHeadingNorth.swapWith(antHeadingSouth);
		assertThat(antHeadingNorth.currentPosition(), equalTo(new Coordinates(3, 1)));
		assertThat(antHeadingSouth.currentPosition(), equalTo(new Coordinates(3, 2)));
	}
	
	@Test
	public void testThatAnAntHeadingBackCanNotSwapPositionsWithAnCollidingAntWhichIsntReturningFoodEither() {
		thrown.expect(IllegalStateException.class);
		thrown.expectMessage("only colliding ants on their way back can swap their positions!");
		Ant antHeadingSouth = new Ant(new Coordinates(3,3), new Coordinates(3,2), new Coordinates(3,1));
		antHeadingSouth.takeFoodUnitFrom(new FoodCell(3));
		Ant antHeadingNorth = new Ant(new Coordinates(2,1), new Coordinates(3,1), new Coordinates(3,2));
		antHeadingNorth.swapWith(antHeadingSouth);
	}
	
	@Test
	public void testThatTwoAntsCanOnlySwapPositionsIfTheyAreHeadedToThePositionOfTheOther() {
		thrown.expect(IllegalStateException.class);
		thrown.expectMessage("only colliding ants on their way back can swap their positions!");
		Ant antHeadingSouth = new Ant(new Coordinates(3,3), new Coordinates(3,2), new Coordinates(3,1));
		antHeadingSouth.takeFoodUnitFrom(new FoodCell(3));
		Ant antHeadingNorth = new Ant(new Coordinates(2,1), new Coordinates(2,2), new Coordinates(2,3));
		antHeadingNorth.takeFoodUnitFrom(new FoodCell(3));
		antHeadingNorth.swapWith(antHeadingSouth);
	}
	
	@Test
	public void testThatAnAntCarryingNoFoodIsDisplayedAsStarOnTheBoard() {
		assertThat(new Ant(new Coordinates(3,3)).printToBoard(), equalTo("*"));
	}
	
	@Test
	public void testThatTheAntIsReadyToDeliverFoodAsSoonItDiscoveredAFoodHill() {
		Ant ant = new Ant(new Coordinates(1,1));
		ant.takeFoodUnitFrom(new FoodCell(5));
		assertThat(ant.readyToDeliverFood(), is(true));
	}
	
	@Test
	public void testThatTheAntKeepsGoingBackThePathItCameWhenItHasToWaitOnAnOtherAntBlockingItsWay() {
		Ant ant = new Ant(new Coordinates(1,1), AntMovingDirection.S);
		ant.move();
		ant.move();
		assertThat(ant.currentPosition(), equalTo(new Coordinates(1,3)));
		ant.takeFoodUnitFrom(new FoodCell(5));
		ant.move();
		assertThat(ant.currentPosition(), equalTo(new Coordinates(1,2)));
		ant.turnOrWait();
		assertThat(ant.currentPosition(), equalTo(new Coordinates(1,2)));
		ant.move();
		assertThat(ant.currentPosition(), equalTo(new Coordinates(1,1)));
	}
	
	@Test
	public void testThatTheAntIsOnHisWayBackAfterItConsumesAFoodUnitFromAFoodCellAndCheckThatThatFoodCellHasAFoodCountDecreasedByOneAfterThat() {
		FoodCell foodCell = new FoodCell(5);
		when(random.randIntIn(eq(1), eq(8))).thenReturn(5)
											.thenReturn(5)
											.thenReturn(3);
		Ant ant = new Ant(new Coordinates(1,1));
		ant.move();
		ant.move();
		ant.turnOrWait();
		ant.move();
		assertThat(ant.currentPosition(), equalTo(new Coordinates(2,3)));
		ant.takeFoodUnitFrom(foodCell);
		ant.move();
		assertThat(ant.currentPosition(), equalTo(new Coordinates(1,3)));
		ant.move();
		ant.move();
		assertThat(ant.currentPosition(), equalTo(new Coordinates(1,1)));
	}
	
	@Test
	public void testThatTheCurrentPositionChangesInRespectToTheGivenDirectionWhenTheAntMoves() {
		when(random.randIntIn(eq(1), eq(8))).thenReturn(1);
		Ant movingAnt = new Ant(new Coordinates(2, 2));
		movingAnt.move();
		assertThat(movingAnt.currentPosition(), equalTo(new Coordinates(2,1)));
	}
	
	@Test
	public void testThatTheAntTurnsToADirectionItWasNotAimedBeforeWhenItTurns() {
		when(random.randIntIn(eq(1), eq(8))).thenReturn(1)
											.thenReturn(1)
											.thenReturn(5);
		Ant turningAnt = new Ant(new Coordinates(2, 2));
		Coordinates formerNextStep = turningAnt.nextStep();
		turningAnt.turnOrWait();
		assertThat(turningAnt.nextStep(), not(equalTo(formerNextStep)));
		assertThat(turningAnt.nextStep(), equalTo(new Coordinates(2,3)));
	}
	
	
	@Test
	public void testCreatingAnAnt() {
		assertThat(new Ant(new Coordinates(1,1)), is(notNullValue()));
	}
	
	@Test
	public void testThatTheAntGetsInitializedWithARandomDirectionInWhichItWillMoveAfterTheFirstStepWhenTheFirstRandomDirectionIsNorth() {
		when(random.randIntIn(eq(1), eq(8))).thenReturn(1);
		assertThat(new Ant(new Coordinates(2, 2)).nextStep(), equalTo(new Coordinates(2,1)));
	}
	
	@Test
	public void testThatTheAntGetsInitializedWithARandomDirectionInWhichItWillMoveAfterTheFirstStepWhenTheFirstRandomDirectionIsNorthEast() {
		when(random.randIntIn(eq(1), eq(8))).thenReturn(2);
		assertThat(new Ant(new Coordinates(2, 2)).nextStep(), equalTo(new Coordinates(3,1)));
	}
	
	@Test
	public void testThatTheAntGetsInitializedWithARandomDirectionInWhichItWillMoveAfterTheFirstStepWhenTheFirstRandomDirectionIsEast() {
		when(random.randIntIn(eq(1), eq(8))).thenReturn(3);
		assertThat(new Ant(new Coordinates(2, 2)).nextStep(), equalTo(new Coordinates(3,2)));
	}
	
	@Test
	public void testThatTheAntGetsInitializedWithARandomDirectionInWhichItWillMoveAfterTheFirstStepWhenTheFirstRandomDirectionIsSouthEast() {
		when(random.randIntIn(eq(1), eq(8))).thenReturn(4);
		assertThat(new Ant(new Coordinates(2, 2)).nextStep(), equalTo(new Coordinates(3,3)));
	}
	
	@Test
	public void testThatTheAntGetsInitializedWithARandomDirectionInWhichItWillMoveAfterTheFirstStepWhenTheFirstRandomDirectionIsSouth() {
		when(random.randIntIn(eq(1), eq(8))).thenReturn(5);
		assertThat(new Ant(new Coordinates(2, 2)).nextStep(), equalTo(new Coordinates(2,3)));
	}
	
	@Test
	public void testThatTheAntGetsInitializedWithARandomDirectionInWhichItWillMoveAfterTheFirstStepWhenTheFirstRandomDirectionIsSouthWest() {
		when(random.randIntIn(eq(1), eq(8))).thenReturn(6);
		assertThat(new Ant(new Coordinates(2, 2)).nextStep(), equalTo(new Coordinates(1,3)));
	}
	
	@Test
	public void testThatTheAntGetsInitializedWithARandomDirectionInWhichItWillMoveAfterTheFirstStepWhenTheFirstRandomDirectionIsWest() {
		when(random.randIntIn(eq(1), eq(8))).thenReturn(7);
		assertThat(new Ant(new Coordinates(2, 2)).nextStep(), equalTo(new Coordinates(1,2)));
	}
	
	@Test
	public void testThatTheAntGetsInitializedWithARandomDirectionInWhichItWillMoveAfterTheFirstStepWhenTheFirstRandomDirectionIsNorthWest() {
		when(random.randIntIn(eq(1), eq(8))).thenReturn(8);
		assertThat(new Ant(new Coordinates(2, 2)).nextStep(), equalTo(new Coordinates(1,1)));
	}
	
	@Test
	public void testThatAnAntIsEqualToItSelf() {
		Ant ant = new Ant(new Coordinates(1, 1));
		assertThat(ant, equalTo(ant));
	}
	
	@Test
	public void testThatAnAntIsCanNotBeEqualToAnOtherAntEvenIfItsStandingOnTheSamePosition() {
		assertThat(new Ant(new Coordinates(1, 1)), not(equalTo(new Ant(new Coordinates(1, 1)))));
	}
}
