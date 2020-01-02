import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import board.Board;
import board.BoardConfiguration;
import board.BoardDimension;
import board.BombCount;
import cells.BombCell;
import cells.RegularCell;
import coordinates.FieldCoordinates;

public class BoardTests {
	
	private Board board;

	@Before
	public void resetBoard() {
		board = new Board();
	}
	
	@Test
	public void testFetchingACellWhichIsAReglularCell() {
		RegularCell expectedCell = new RegularCell();
		FieldCoordinates expectedCorrdinates = FieldCoordinates.createFor(1, 1);
		board.putToBoard(expectedCorrdinates, expectedCell);
		board.fetchCell(expectedCorrdinates , (bomb)    -> failWith("impossible"),
									  		  (regular) -> assertEqual(regular, expectedCell));
	}
	
	@Test
	public void testFetchingACellWhichIsABombCell() {
		FieldCoordinates expectedCorrdinates = FieldCoordinates.createFor(1, 1);
		BombCell expectedCell = new BombCell();
		
		board.putToBoard(FieldCoordinates.createFor(0, 0), new RegularCell());
		board.putToBoard(expectedCorrdinates, expectedCell);
		board.fetchCell(expectedCorrdinates , (bomb)    -> assertEqual(bomb, expectedCell),
									  		  (regular) -> failWith("impossible"));
	}
	
	@Test
	public void testFetchingTheNeighborsOfABoardCellWhenThereAreSome() {
		board = new BoardConfiguration(BombCount.Of(1), new BoardDimension(3, 3)).createBoard();
		final List<FieldCoordinates> actualNeighbors = new ArrayList<FieldCoordinates>();
		board.forNeighbours(FieldCoordinates.createFor(1, 1), (cell, neighbor) -> actualNeighbors.add(neighbor));
		assertThat(actualNeighbors, hasItems(FieldCoordinates.createFor(2, 1),
											 FieldCoordinates.createFor(1, 2),
											 FieldCoordinates.createFor(2, 2)));
		assertThat(actualNeighbors, Matchers.hasSize(3));
	}
	
	private Void assertEqual(Object obj1, Object obj2) {
		assertThat(obj1, equalTo(obj2));
		return null;
	}
	
	private Void failWith(String message) {
		fail(message);
		return null;
	}
}
