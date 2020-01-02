package board;
import java.util.function.Supplier;

public class AdjacentBombs {
	private int bombs;
	public static AdjacentBombs notSet() { return new AdjacentBombs(-1); }
	public static AdjacentBombs noBombs() { return new AdjacentBombs(0); }
	
	private AdjacentBombs(int initalCount) {
		this.bombs = initalCount;
	}
	
	public Void increase() {
		bombs++;
		return null;
	}
	
	public Void whenBombs(Supplier<?> bombCase, Supplier<?> noBombs) {
		if (equals(AdjacentBombs.notSet()) || equals(AdjacentBombs.noBombs())) {
			noBombs.get();
			return null;
		}
		bombCase.get();
		return null;
	}
	
	public Void printCount() {
		System.out.print(bombs);
		return null;
	}

	@Override
	public int hashCode() {
		return 31 * bombs;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		return getClass() == obj.getClass() ? AreEqual((AdjacentBombs)obj) : false;
	}
	
	private boolean AreEqual(AdjacentBombs other) {
		return other.bombs == bombs;
	}
}
