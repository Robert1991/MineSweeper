package coordinates;
import java.util.function.Supplier;

public class FieldCoordinates implements Comparable<FieldCoordinates> {
	private int xCoordinate;
	private int yCoordinate;
	
	public static FieldCoordinates createFor(int xPosition, int yPosition) {
		if (xPosition < 0 || yPosition < 0) throw new IllegalArgumentException("the coordinates can not be negative");
		return new FieldCoordinates(xPosition, yPosition);
	}
	
	public FieldCoordinates(int xPosition, int yPosition) {
		this.xCoordinate = xPosition;
		this.yCoordinate = yPosition;
	}

	public void ifNeighbor(FieldCoordinates other, Supplier<?> action) {
		if (!equals(other) && inNeighborHood(other)) action.get();
	}
	
	public void sameX(FieldCoordinates other, Supplier<?> endCase, Supplier<?> baseCase) {
		if (other.xCoordinate == xCoordinate) {
			endCase.get();
			return;
		}
		baseCase.get();
	}

	@Override
	public int compareTo(FieldCoordinates other) {
		if (this.yCoordinate > other.yCoordinate ||
		   (this.yCoordinate == other.yCoordinate && this.xCoordinate > other.xCoordinate))
			return 1; 
		return areEqual(other) ? 0 : -1;
	}
	
	@Override
	public int hashCode() {
		int result = 1;
		result = 31 * result + xCoordinate;
		result = 31 * result + yCoordinate;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null) return areEqual(obj);
		return false;
	}
	
	private boolean inNeighborHood(FieldCoordinates other) {
		return Math.abs(other.xCoordinate - this.xCoordinate) <= 1 && Math.abs(other.yCoordinate - this.yCoordinate) <= 1;
	}

	private boolean areEqual(Object obj) {
		if (getClass() == obj.getClass()) return equalCoordinates((FieldCoordinates)obj);
		return false;
	}

	private boolean equalCoordinates(FieldCoordinates other) {
		if (xCoordinate != other.xCoordinate || yCoordinate != other.yCoordinate) return false;
		return true;
	}
}
