package board;

import cells.BoardCell;
import cells.RegularCell;
import coordinates.FieldCoordinates;

public class CellRevealer {
	private Board board;

	public CellRevealer(Board board) {
		this.board = board;
	}
	
	public Void reveal(RegularCell regularCell, FieldCoordinates coordinates) {
		final Neighbourhood adjacent = Neighbourhood.createFor(board, regularCell, coordinates);
		adjacent.noBombs((neighborHood) -> continueCounting(neighborHood, coordinates));
		return null;
	}
	
	private Void continueCounting(Neighbourhood neighborHood, FieldCoordinates coordinates) {
		board.forNeighbours(coordinates, (cell, neighborCoordinates) -> countNeighbours(neighborHood, cell, neighborCoordinates));
		return null;
	}
	
	private Void countNeighbours(Neighbourhood neighbourHood, BoardCell cell, FieldCoordinates neighbuorCoordinates) {
		neighbourHood.canCount(cell, (regular) -> countFor(regular, neighbuorCoordinates));
		return null;
	}
	
	private Void countFor(RegularCell regular, FieldCoordinates neighborCoordinates) {
		regular.ifUnreleaved(() -> reveal(regular, neighborCoordinates));
		return null;
	}
}
