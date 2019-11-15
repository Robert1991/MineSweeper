package board;

import java.util.Collection;
import java.util.function.Supplier;

import cells.BoardCell;

public class UncoveredCount {
	private int currentCount;

	public UncoveredCount() {
		this.currentCount = 0;
	}
	
	public Void increase() {
		currentCount++;
		return null;
	}
	
	public Void ifAllUncovered(Collection<BoardCell> cells, Supplier<?> allUncovered) {
		if (cells.size() == currentCount + 1) 
			allUncovered.get();
		return null;
	}
}
