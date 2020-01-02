package ants.test.cells;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ants.ants.Ant;
import ants.board.cells.AntHillCell;
import ants.board.cells.Coordinates;
import ants.board.cells.GameCell;
import ants.board.cells.NoMoreAntsThereException;

public class AntHillCellTests {
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testThatTheAntCountOnTheAntHillHasToBeGreaterThanOne() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("There has to be at least one ant in that hill.");
		GameCell.antHill(0, new Coordinates(2, 3));
	}
	
	@Test
	public void testCreatingAAntHillGameCellWithoutFoodAndCheckThatItsToStringIsAnA() {
		assertThat(GameCell.antHill(3, new Coordinates(2, 3)).printOut(), equalTo("A"));
	}
	
	@Test
	public void testThatAnAntHillCellIsNotEqualToAnotherAntHillCellWhichHasADifferentAntCount() {
		assertThat(GameCell.antHill(5, new Coordinates(2, 3)), not(equalTo(GameCell.antHill(3, new Coordinates(2, 3)))));
	}
	
	@Test
	public void testThatAnAntHillCellIsNotEqualToAnEmptyCell() {
		assertThat(GameCell.antHill(5, new Coordinates(2, 3)), not(equalTo(GameCell.empty())));
	}
	
	@Test
	public void testThatAnAntHillCellIsEqualToAnOtherAntHillCellWithTheSameAntCount() {
		assertThat(GameCell.antHill(5, new Coordinates(2, 3)), equalTo(GameCell.antHill(5, new Coordinates(2, 3))));
	}
	
	@Test
	public void testThatTheFoodCountOnTheAntHillIncreasesByOneWhenAnAntDeliversSomeFood() {
		AntHillCell antHill = (AntHillCell)GameCell.antHill(5, new Coordinates(2, 3));
		assertThat(antHill.foodUnitsDelivered(), equalTo(0));
		antHill.antCameBackWithFood();
		assertThat(antHill.foodUnitsDelivered(), equalTo(1));
		assertThat(antHill.antsLeft(), equalTo(6));
	}
	
	@Test
	public void testThatTheAntHasTheSamePositionAsTheAntHillWhenTheCurrentCoordinatesOfTheAntAndTheCoordinatesOfTheAntHillAreEqual() {
		assertThat(new AntHillCell(3, new Coordinates(1,1)).isAntOccupingHill(new Ant(new Coordinates(1,1))),
				   is(true));
	}
	
	@Test
	public void testThatTheAnAntIsNotOccupingTheAntHillWhenItsPositionDiffersFromTheAntHillPosition() {
		assertThat(new AntHillCell(3, new Coordinates(1,1)).isAntOccupingHill(new Ant(new Coordinates(1,2))),
				   is(false));
	}
		
	@Test
	public void testThatTheAntHillCanOnlyCreateAsMuchAntsAsThereWhereGivenInTheFirstPlace() {
		thrown.expect(NoMoreAntsThereException.class);
		AntHillCell antHill = (AntHillCell)GameCell.antHill(3, new Coordinates(2, 3));
		assertThat(antHill.antCanBeReleased(), is(true));
		assertThat(antHill.antsLeft(), is(equalTo(3)));
		assertThat(antHill.releaseNextAnt(), instanceOf(Ant.class));
		assertThat(antHill.antCanBeReleased(), is(true));
		assertThat(antHill.antsLeft(), is(equalTo(2)));
		assertThat(antHill.releaseNextAnt(), instanceOf(Ant.class));
		assertThat(antHill.antCanBeReleased(), is(true));
		assertThat(antHill.antsLeft(), is(equalTo(1)));
		assertThat(antHill.releaseNextAnt(), instanceOf(Ant.class));
		assertThat(antHill.antCanBeReleased(), is(false));
		antHill.releaseNextAnt();
	}
}
