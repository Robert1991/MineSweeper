package cells;

import java.util.function.Supplier;

public abstract class BoardCell {
	public static String CoveredField = "#";
	protected boolean wasRevealed = false;
	
	public abstract void printToConsole();
	
	public abstract Void revealIdentity();

	public Void Reveal() {
		wasRevealed = true;
		return null;
	}
	
	public Void ifRevealed(Supplier<?> revealed) {
		if (wasRevealed) {
			revealed.get(); 
			return null;
		}
		return null;
	}
	
}
