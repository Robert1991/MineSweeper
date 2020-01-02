package board;
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

	public Map<FieldCoordinates, BoardCell> cells;

	
	public Board() {
		cells = new TreeMap<FieldCoordinates, BoardCell>();
	}
	
	public void putToBoard(FieldCoordinates coordinates, BoardCell cell) {
		cells.put(coordinates, cell);
	}
	
	public Void fetchCell(FieldCoordinates coordinates, Function<BombCell, ?> bombCase, 
			Function<RegularCell, ?> regularCase) {
		BoardCell cell = cells.get(coordinates);
		if (cell instanceof BombCell) {
			bombCase.apply((BombCell) cell);
			return null;
		}
		regularCase.apply((RegularCell) cell);
		return null;
	}
	
	public BoardCell fetchCell(FieldCoordinates coordinates) {
		return cells.get(coordinates);
	}
	
	
	public NeighborHood neighborHoodOf(FieldCoordinates coordinates) {
		NeighborHood neighbors = NeighborHood.create();
		forNeighbours(coordinates, 
				(neighborcell, neighborCoordinates) -> neighbors.put(neighborCoordinates, 
																	 neighborcell));
		return neighbors;
	}
	
	public Void forNeighbours(FieldCoordinates coordinates, BiFunction<BoardCell, FieldCoordinates, ?> action) {
		forEachCell((boardCoordinates, cell) -> 
				coordinates.ifNeighbor(boardCoordinates, () -> action.apply(cell, boardCoordinates)));
		return null;
	}
	
	public Void forRegularNeighbours(FieldCoordinates coordinates, BiFunction<RegularCell, FieldCoordinates, ?> action) {
		forEachCell((boardCoordinates, cell) -> 
				coordinates.ifNeighbor(boardCoordinates, applyIfRegular(action, boardCoordinates, cell)));
		return null;
	}

	public void forEachCell(BiConsumer<FieldCoordinates, BoardCell> cellFunction) {
		cells.forEach(cellFunction);
	}
	
	public void forEachBoardCell(Consumer<BoardCell> cellConsumer) {
		cells.values().forEach(cellConsumer);
	}
	
	private Supplier<?> applyIfRegular(BiFunction<RegularCell, FieldCoordinates, ?> action, 
			FieldCoordinates boardCoordinates, BoardCell cell) {
		if (cell instanceof RegularCell)
			return () -> action.apply((RegularCell)cell, boardCoordinates);
		return () -> null;
	}
}
