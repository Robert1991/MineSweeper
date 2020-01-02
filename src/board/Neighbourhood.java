package board;
import java.util.function.Function;
import java.util.function.Supplier;
import cells.BoardCell;
import cells.BombCell;
import cells.RegularCell;
import coordinates.FieldCoordinates;

public class Neighbourhood {
	private int bombs;
	public static Neighbourhood notSet() { return new Neighbourhood(-1); }
	public static Neighbourhood noBombs() { return new Neighbourhood(0); }
	
	public static Neighbourhood createFor(Board board, RegularCell regular, FieldCoordinates coordinates) {
		final Neighbourhood adjacent = Neighbourhood.noBombs();
		board.forNeighbours(coordinates, (cell, neighborCoordinates) -> adjacent.analyze(cell));
		regular.wasRevealedWith(adjacent);
		return adjacent;
	}
	
	private Neighbourhood(int initalCount) {
		this.bombs = initalCount;
	}
	
	public Void whenBombs(Supplier<?> bombCase, Supplier<?> noBombs) {
		if (equals(Neighbourhood.notSet()) || equals(Neighbourhood.noBombs())) {
			noBombs.get();
			return null;
		}
		bombCase.get();
		return null;
	}
	
	public Void canCount(BoardCell cell, Function<RegularCell, ?> action) {
		if (cell instanceof RegularCell) action.apply((RegularCell)cell);
		return null;
	}
	
	public void noBombs(Function<Neighbourhood, ?> continueCase) {
		if (bombs == 0) continueCase.apply(this);
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
		return getClass() == obj.getClass() ? AreEqual((Neighbourhood)obj) : false;
	}
	
	private boolean AreEqual(Neighbourhood other) {
		return other.bombs == bombs;
	}

	private Void analyze(BoardCell cell) {
		if (cell instanceof BombCell) bombs++;
		return null;
	}
}
