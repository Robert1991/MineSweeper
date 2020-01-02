package game;
import board.BoardConfiguration;
import board.BoardController;
import board.BombExploded;
import coordinates.FieldCoordinates;

public class GameBoard {
	private BoardController boardController;

	public static GameBoard createFrom(BoardConfiguration configuration) {
		return new GameBoard(configuration);
	}
	
	public GameBoard(BoardConfiguration configuration) {
		this.boardController = new BoardController(configuration);
	}
	
	public void putBoardToConsole() {
		boardController.printBoard();
	}
	
	public GameStatus reveal(FieldCoordinates coordinates) {
		try {
			boardController.reveal(coordinates, (bombCell) -> bombCell.explode());
			return GameStatus.OnGoing;
		} catch(BombExploded exc) {
			System.out.println("you looser");
			revealBoard();
			return GameStatus.Lost;
		}
	}
	
	public void revealBoard() {
		boardController.revealBoard();
	}
}
