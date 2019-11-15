package cells;
import board.BombExploded;

public class BombCell extends BoardCell {
	private final String identity = "*";
	
	@Override
	public void printToConsole() {
		if (wasRevealed) {
			System.out.print(identity);
			return;
		}
		System.out.print(CoveredField);
	}
	
	public Void explode() throws BombExploded {
		throw new BombExploded();
	}

	@Override
	public Void revealIdentity() {
		this.wasRevealed = true;
		return null;
	}
	
}
