import org.junit.Test;

import board.BoardConfiguration;
import board.BoardDimension;
import board.BombCount;
import coordinates.FieldCoordinates;
import game.GameBoard;

public class GameBoardTests {
	@Test
	public void TestPrintingOutTheGameBoard() {
		GameBoard board = GameBoard.createFrom(new BoardConfiguration(BombCount.Of(3), new BoardDimension(5, 5)));
		board.putBoardToConsole();
		System.out.println("-----------------");
		board.reveal(FieldCoordinates.createFor(5, 5));
		board.putBoardToConsole();
		System.out.println("-----------------");
		board.revealBoard();
	}
}
