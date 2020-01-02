package ants.random;
import java.util.Random;

public class RandomWrapper {
	public int randIntIn(int lower, int upper) {
		if (lower < upper) {			
			return nextRandomIntIn(lower, upper);
		} else {
			throw new IllegalArgumentException("lower bound has to be smaller than upper bound by at least 1");
		}
	}
	
	private int nextRandomIntIn(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}
}
