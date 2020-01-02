package cells;
import java.util.function.Supplier;

import board.AdjacentBombs;

public class RegularCell extends BoardCell {
	private final String identity = ".";
	private AdjacentBombs neighborhoodBombs = AdjacentBombs.notSet();

	public Void wasRevealedWith(AdjacentBombs bombs) {
		this.wasRevealed = true;
		this.neighborhoodBombs = bombs;
		return null;
	}
	
	@Override
	public void printToConsole() {
		if (wasRevealed) {
			printRevealed();
			return;
		}
		System.out.print(CoveredField);
	}
	
	public Void ifUnreleaved(Supplier<?> action) {
		if (!wasRevealed)
			action.get();
		return null;
	}

	private void printRevealed() {
		neighborhoodBombs.whenBombs(() -> neighborhoodBombs.printCount(),
									 () -> printIdentity());
	}

	private Void printIdentity() {
		System.out.print(identity);
		return null;
	}

	@Override
	public Void revealIdentity() {
		this.wasRevealed = true;
		this.neighborhoodBombs = AdjacentBombs.notSet();
		return null;
	}
}
