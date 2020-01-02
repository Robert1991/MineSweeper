package ants.board.cells;


public abstract class GameCell {
	public abstract String printOut();

	public static GameCell empty() {
		return new EmptyCell();
	}

	public static GameCell antHill(int antCount, Coordinates coordinates) {
		if (antCount < 1)
			throw new IllegalArgumentException("There has to be at least one ant in that hill.");
		return new AntHillCell(antCount, coordinates);
	}

	public static GameCell foodCellWith(int foodCount) {
		return new FoodCell(foodCount);
	}
	
	
}
