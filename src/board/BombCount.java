package board;
import java.util.Random;
import java.util.function.Supplier;

import cells.BoardCell;

public class BombCount {
	public Random random = new Random();
	private int bombCount;
	
	public static BombCount Of(int count) {
		if (count < 1)
			throw new IllegalArgumentException("bomb count has to be greater or equal to 1");
		return new BombCount(count);
	}
	
	private BombCount(int count) {
		this.bombCount = count;
	}
	
	public BoardCell nextCell(Supplier<BoardCell> bombCase,
							  Supplier<BoardCell> baseCase) {
		if (random.nextDouble() <= 0.3 && bombCount >= 1) {
			bombCount--;
			return bombCase.get();
		}
		return baseCase.get();
	}
}
