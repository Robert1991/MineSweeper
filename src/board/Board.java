package board;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import cells.BoardCell;
import cells.BombCell;
import cells.RegularCell;
import coordinates.FieldCoordinates;

public class Board {
	private Map<FieldCoordinates, BoardCell> cells;
	
	public Board() {
		cells = new TreeMap<FieldCoordinates, BoardCell>();
	}
	
	public void putToBoard(FieldCoordinates coordinates, BoardCell cell) {
		cells.put(coordinates, cell);
	}
	
	public void allRevealed(Supplier<?> revealCase, Supplier<?> baseCase) {
		someCovered(revealCase);
	}

	public void revealCell(FieldCoordinates coordinates, Function<BombCell, ?> bombCase, 
											  			 Function<RegularCell, ?> regularCase) {
		BoardCell cell = cells.get(coordinates);
		if (cell instanceof BombCell) {
			bombCase.apply((BombCell) cell);
		}
		regularCase.apply((RegularCell) cell);
	}
	
	public Void forNeighbours(FieldCoordinates coordinates, BiFunction<BoardCell, FieldCoordinates, ?> action) {
		forEachCell((boardCoordinates, cell) -> coordinates.ifNeighbor(boardCoordinates, 
				() -> action.apply(cell, boardCoordinates)));
		return null;
	}

	public void forEachCell(BiConsumer<FieldCoordinates, BoardCell> cellFunction) {
		cells.forEach(cellFunction);
	}
	
	public void forEachBoardCell(Consumer<BoardCell> cellConsumer) {
		cells.values().forEach(cellConsumer);
	}
	
	private void someCovered(Supplier<?> revealCase) {
		Collection<BoardCell> boardCells = cells.values();
		final UncoveredCount count = new UncoveredCount();
		boardCells.forEach(field -> field.ifRevealed(() -> count.increase()));
		count.ifAllUncovered(boardCells, revealCase);
	}
}
