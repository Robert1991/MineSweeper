package cells;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class BoardCell {
	public static String CoveredField = "#";
	protected boolean wasRevealed = false;
	
	public abstract void printToConsole();
	
	public abstract Void revealIdentity();

	public Void reveal() {
		System.out.println("revealed!");
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
	
	public Void ifNotRevealed(Supplier<?> notRevealed) {
		if (!wasRevealed) {
			notRevealed.get(); 
			return null;
		}
		return null;
	}
	
	public Void ifRevealed(Function<BoardCell,?> revealed, Function<BoardCell, ?> unrevaled) {
		if (wasRevealed) {
			revealed.apply(this); 
			return null;
		}
		unrevaled.apply(this);
		return null;
	}
}
