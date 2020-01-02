package board;

import java.util.function.Function;
import cells.BoardCell;
import cells.BombCell;
import cells.RegularCell;
import coordinates.FieldCoordinates;

public class BoardController {
	private Board board;
	private BoardConfiguration boardConfiguration;

	public BoardController(BoardConfiguration boardConfigration) {
		this.board = boardConfigration.createBoard();
		this.boardConfiguration = boardConfigration;
	}
	
	public void printBoard() {
		boardConfiguration.printInDimension(board);
	}
	
	public Void reveal(FieldCoordinates coordinates, Function<BombCell, ?> bombCase) {
		BoardCell cell = board.fetchCell(coordinates);
		if (cell instanceof BombCell)
			bombCase.apply((BombCell)cell);
		else 
			revealCell((RegularCell) cell, coordinates);
		return null;
	}
	
	public void revealBoard() {
		board.forEachCell((coordinates, cell) -> cell.revealIdentity());
		boardConfiguration.printInDimension(board);
	}
	
	private Void revealCell(RegularCell cell, FieldCoordinates coordinates) {
		NeighborHood neighbors = board.neighborHoodOf(coordinates);
		neighbors.countBombs((bombCount) -> cell.wasRevealedWith(bombCount));
		neighbors.noBombsInNeigborHood(() -> revealNeighborhood(coordinates));
		return null;
	}

	private Void revealNeighborhood(FieldCoordinates coordinates) {
		board.forRegularNeighbours(coordinates, 
			(cell, neighborCoordinates) -> revealIfPossible(cell, neighborCoordinates));
		return null;
	}
	
	private Void revealIfPossible(RegularCell regular, FieldCoordinates neighborCoordinates) {
		regular.ifNotRevealed(() -> revealCell(regular, neighborCoordinates));
		return null;
	}
}
