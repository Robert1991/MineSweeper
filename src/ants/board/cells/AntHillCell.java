package ants.board.cells;
import ants.ants.Ant;

public class AntHillCell extends GameCell {
	private int antCount;
	private Coordinates coordinates;
	private int foodUnitCount;

	public AntHillCell(int antCount, Coordinates coordinates) {
		this.antCount = antCount;
		this.coordinates = coordinates;
	}
	
	@Override
	public String printOut() {
		return "A";
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof AntHillCell)
			return this.antCount == ((AntHillCell)other).antCount;
		return false;
	}
	
	public int antsLeft() {
		return antCount;
	}

	public Ant releaseNextAnt() {
		if (antCount > 0) {
			antCount--;
			return new Ant(coordinates);
		}
		throw new NoMoreAntsThereException();
	}

	public boolean antCanBeReleased() {
		return antCount > 0;
	}

	public boolean isAntOccupingHill(Ant ant) {
		return ant.currentPosition().equals(coordinates);
	}

	public int foodUnitsDelivered() {
		return foodUnitCount;
	}

	public void antCameBackWithFood() {
		foodUnitCount++;
		antCount++;
	}
}
