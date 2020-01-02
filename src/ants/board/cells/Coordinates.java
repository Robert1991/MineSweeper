package ants.board.cells;

public class Coordinates implements Comparable<Coordinates> {

	public int xCoordinate;
	public int yCoordinate;

	public Coordinates(int xCoordinate, int yCoordinate) {
		if (xCoordinate < 0 ||
			yCoordinate < 0)
			throw new IllegalArgumentException(
					"Cooridnates have to be positive!");
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}
	
	public Coordinates shiftWith(int xOffset, int yOffset) {
		return new Coordinates(xCoordinate + xOffset, yCoordinate + yOffset);
	}
	
	@Override
	public String toString() {
		return String.format("{x:%d, y:%d}", xCoordinate, yCoordinate);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + xCoordinate;
		result = prime * result + yCoordinate;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinates other = (Coordinates) obj;
		if (xCoordinate != other.xCoordinate)
			return false;
		if (yCoordinate != other.yCoordinate)
			return false;
		return true;
	}

	@Override
	public int compareTo(Coordinates other) {		
		if (this.equals(other))
			return 0;
		else if (this.yCoordinate > other.yCoordinate)
			return 1;
		else if (this.xCoordinate > other.xCoordinate &&
				 this.yCoordinate == other.yCoordinate)
			return 1;
		return -1;
	}
}
