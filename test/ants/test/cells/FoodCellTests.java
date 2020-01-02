package ants.test.cells;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.NoSuchElementException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ants.board.cells.FoodCell;
import ants.board.cells.GameCell;

public class FoodCellTests {
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testThatAFoodCellIsInitializedWithTheFoodUnitCountAndPrintsThatOutWhenCallingPrintOut() {
		assertThat(GameCell.foodCellWith(3).printOut(), equalTo("3"));
	}
		
	@Test
	public void testThatAFoodCellIsNotEqualToAnotherFoodCellWithADifferentFoodCount() {
		assertThat(GameCell.foodCellWith(5), not(equalTo(GameCell.foodCellWith(3))));
	}
	
	@Test
	public void testThatAFoodCellIsEqualToAnotherFoodCellWithTheSameFoodCount() {
		assertThat(GameCell.foodCellWith(5), equalTo(GameCell.foodCellWith(5)));
	}
	
	@Test
	public void testThatAFoodCellIsNotEqualToAnEmptyCell() {
		assertThat(GameCell.foodCellWith(5), not(equalTo(GameCell.empty())));
	}
	
	@Test
	public void testThatTheFoodCountInTheFoodCellDecreasesByOneWhenAnAntTakesSomeFoodFromIt() {
		FoodCell cell = new FoodCell(3);
		cell.takeFoodUnit();
		assertThat(cell.currentFoodCount(), equalTo(2));
	}
	
	@Test
	public void testThatAFoodCellIsEmptyWhenAllFoodWasTakenFromIt() {
		FoodCell cell = new FoodCell(2);
		cell.takeFoodUnit();
		cell.takeFoodUnit();
		assertThat(cell.isEmpty(), equalTo(true));
	}
	
	@Test
	public void testThatExceptionIsThrownWhenTryingToConsumeFromAFoodCellWhichIsAlreadyEmpty() {
		thrown.expect(NoSuchElementException.class);
		thrown.expectMessage("There is no more food left here!");
		FoodCell cell = new FoodCell(1);
		cell.takeFoodUnit();
		cell.takeFoodUnit();
	}
}
