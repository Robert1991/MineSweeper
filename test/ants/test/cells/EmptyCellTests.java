package ants.test.cells;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import ants.board.cells.Coordinates;
import ants.board.cells.GameCell;


public class EmptyCellTests {
	@Test
	public void testCreatingAnEmptyGameCellWithoutFoodAndCheckThatItsToStringIsJustAPoint() {
		assertThat(GameCell.empty().printOut(), equalTo("."));
	}
	
	@Test
	public void testThatAnInstanceOfAGameCellIsAlwaysEqualToAnotherInstanceOfAGameCell() {
		assertThat(GameCell.empty(), equalTo(GameCell.empty()));
	}
	
	@Test
	public void testThatAnInstanceOfAGameCellIsNotEqualToACellOfAnOtherKinde() {
		assertThat(GameCell.empty(), both(not(equalTo(GameCell.foodCellWith(3))))
									.and(not(equalTo(GameCell.antHill(1, new Coordinates(1,1))))));
	}
}
