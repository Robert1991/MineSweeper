package game;
import board.Board;
import board.BoardConfiguration;
import board.BombExploded;
import board.CellRevealer;
import cells.RegularCell;
import coordinates.FieldCoordinates;

public class GameBoard {
	private Board board;
	private BoardConfiguration configuration;

	public static GameBoard createFrom(BoardConfiguration configuration) {
		return new GameBoard(configuration);
	}
	
	public GameBoard(BoardConfiguration configuration) {
		this.board = configuration.createBoard();
		this.configuration = configuration;
	}
	
	public void putBoardToConsole() {
		configuration.printBoard(board);
	}
	
	public GameStatus reveal(FieldCoordinates coordinates) {
		try {
			board.revealCell(coordinates, (bombCell) -> bombCell.explode(),
										  (regularCell) -> revealCell(regularCell, coordinates));
			return GameStatus.OnGoing;
		} catch(BombExploded exc) {
			System.out.println("you looser");
			revealBoard();
			return GameStatus.Lost;
		}
	}
	
	public void revealBoard() {
		board.forEachCell((coordinates, cell) -> cell.revealIdentity());
		configuration.printBoard(board);
	}
	
	private Void revealCell(RegularCell cell, FieldCoordinates coordinates) {
		CellRevealer revealer = new CellRevealer(board);
		revealer.reveal(cell, coordinates);
		return null;
	}
}
