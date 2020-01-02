package ants.board.dimension;
import java.util.ArrayList;
import java.util.List;

public class GameObjectPositions {
	public int antHillPosition;
	public List<Integer> foodCellPositions = new ArrayList<Integer>();
	
	public GameObjectPositions(int antHillPosition, List<Integer> foodCellPositions) {
		this.antHillPosition = antHillPosition;
		this.foodCellPositions = foodCellPositions;
	}
	
	public boolean isAntHillPosition(int positionOnBoard) {
		return positionOnBoard == antHillPosition;
	}
	
	public boolean isFoodCellPosition(int positionOnBoard) {
		return foodCellPositions.contains(positionOnBoard);
	}
}
