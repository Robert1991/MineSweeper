package ants.ants;
import java.util.Arrays;
import java.util.Optional;

import ants.random.RandomWrapper;

public enum AntMovingDirection {
	N(1),
	NE(2),
	E(3),
	SE(4),
	S(5),
	SW(6),
	W(7),
	NW(8);
	
	public int number;
	AntMovingDirection(int number) {
		this.number = number;
	}
	
	public static RandomWrapper randomWrapper = new RandomWrapper();
	
	public static AntMovingDirection random() {
		int next = randomWrapper.randIntIn(1, 8);
		Optional<AntMovingDirection> movingDir = Arrays.stream(AntMovingDirection.values())
					 .filter(entry -> entry.number == next)
					 .findFirst();
		return movingDir.get();
	}
}
