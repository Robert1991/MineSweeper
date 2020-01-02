package board;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import cells.BoardCell;
import cells.BombCell;
import cells.RegularCell;
import coordinates.FieldCoordinates;

public class NeighborHood {
	private Map<FieldCoordinates, BoardCell> neighborHood = new TreeMap<FieldCoordinates, BoardCell>();

	public static NeighborHood create() {
		return new NeighborHood();
	}
	
	public Void put(FieldCoordinates coordinates, BoardCell cell) {
		neighborHood.put(coordinates, cell);
		return null;
	}
	
	public Void countBombs(Function<AdjacentBombs, ?> bombCount) {
		bombCount.apply(countBombs());
		return null;
	}
	
	public Void reveal(RegularCell cell) {
		AdjacentBombs counted = countBombs();
		cell.wasRevealedWith(counted);
		return null;
	}
	
	public Void noBombsInNeigborHood(Supplier<?> continueCase) {
		AdjacentBombs counted = countBombs();
		if (counted.equals(AdjacentBombs.noBombs()))
			continueCase.get();
		return null;
	}
	
	public AdjacentBombs countBombs() {
		final AdjacentBombs bombs = AdjacentBombs.noBombs();
		forAllBombs((bomb) -> bombs.increase());
		return bombs;
	}
	
	private Void forAllBombs(Consumer<BoardCell> action) {
		Collection<BoardCell> cells = neighborHood.values();
		cells.stream().filter(cell -> filterBombs(cell))
					  .forEach(action);
		return null;
	}
	
	private boolean filterBombs(BoardCell cell) {
		if (cell instanceof BombCell)
			return true;
		return false;
	}
}
