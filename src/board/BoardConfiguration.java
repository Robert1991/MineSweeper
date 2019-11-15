package board;
import cells.BoardCell;
import cells.BombCell;
import cells.RegularCell;
import coordinates.FieldCoordinates;

public class BoardConfiguration {
	private BombCount bombCount;
	private BoardDimension boardDimension;
	
	public BoardConfiguration(BombCount bombCount, BoardDimension boardDimension) {
		super();
		this.bombCount = bombCount;
		this.boardDimension = boardDimension;
	}
	
	public Board createBoard() {
		Board board = new Board();
		for(FieldCoordinates coordinates : boardDimension.dimensionCoordinates()) {
			board.putToBoard(coordinates, bombCount.nextCell(() -> new BombCell(), 
															 () -> new RegularCell()));
		}
		return board;
	}
	
	public void printBoard(Board board) {
		board.forEachCell((coordinates, cell) -> {
			coordinates.sameX(boardDimension.xScale(), () -> printEnd(cell), () -> printCell(cell));
		});
	}

	private Void printEnd(BoardCell cell) {
		cell.printToConsole();
		System.out.println();
		return null;
	}
	
	private Void printCell(BoardCell cell) {
		cell.printToConsole();
		return null;
	}
}	
