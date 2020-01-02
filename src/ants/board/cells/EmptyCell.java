package ants.board.cells;

public class EmptyCell extends GameCell {
	@Override
	public String printOut() {
		return ".";
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof EmptyCell)
			return true;
		return false;
	}
}
