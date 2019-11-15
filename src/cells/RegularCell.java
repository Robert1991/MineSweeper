package cells;
import java.util.function.Supplier;

import board.Neighbourhood;

public class RegularCell extends BoardCell {
	private final String identity = ".";
	private Neighbourhood neighbourhoodBombs = Neighbourhood.notSet();

	public void wasRevealedWith(Neighbourhood bombs) {
		this.wasRevealed = true;
		this.neighbourhoodBombs = bombs;
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
		neighbourhoodBombs.whenBombs(() -> neighbourhoodBombs.printCount(),
									 () -> printIdentity());
	}

	private Void printIdentity() {
		System.out.print(identity);
		return null;
	}

	@Override
	public Void revealIdentity() {
		this.wasRevealed = true;
		this.neighbourhoodBombs = Neighbourhood.notSet();
		return null;
	}
}
