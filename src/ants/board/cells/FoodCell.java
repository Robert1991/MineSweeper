package ants.board.cells;
import java.util.NoSuchElementException;

public class FoodCell extends GameCell {
	private int foodCount;
	
	public FoodCell(int foodCount) {
		this.foodCount = foodCount;
	}
	
	public int currentFoodCount() {
		return foodCount;
	}

	@Override
	public String printOut() {
		return String.valueOf(foodCount);
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof FoodCell)
			return ((FoodCell)other).foodCount == this.foodCount;
		return false;
	}

	public void takeFoodUnit() {
		if (!isEmpty())
			foodCount--;
		else
			throw new NoSuchElementException("There is no more food left here!");
	}

	public boolean isEmpty() {
		return foodCount == 0;
	}
}
