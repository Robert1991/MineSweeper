package ants.ants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import ants.board.cells.Coordinates;
import ants.board.cells.FoodCell;

public class Ant {
	private List<Coordinates> path = new ArrayList<Coordinates>();
	private AntMovingDirection currentDirection;
	private boolean packedWithFood = false;
	private UUID antId;
	
	public static String showOnBoard() {
		return "*";
	}
	
	public Ant(Coordinates startingPosition) {
		this.path.add(startingPosition);
		this.currentDirection = AntMovingDirection.random();
		this.packedWithFood = false;
		this.antId = UUID.randomUUID();
	}
	
	public Ant(Coordinates... pathFromAntHill) {
		this.path.addAll(Arrays.asList(pathFromAntHill));
		this.currentDirection = AntMovingDirection.random();
		this.packedWithFood = false;
		this.antId = UUID.randomUUID();
	}
	
	public Ant(Coordinates startingPosition, AntMovingDirection fixedStartingDirection) {
		this.path.add(startingPosition);
		this.currentDirection = fixedStartingDirection;
		this.antId = UUID.randomUUID();
	}
	
	public Coordinates move() {
		Coordinates nextStepCoordinates = nextStep();
		
		if (packedWithFood) {
			path.remove(path.size()-1);
		} else {			
			path.add(nextStepCoordinates);
		}
		
		return nextStepCoordinates;
	}
	
	public Coordinates currentPosition() {
		return path.get(path.size()-1);
	}
	
	public void turnOrWait() {
		if (!packedWithFood) {
			turnToNewDirection();			
		}
	}

	public Coordinates nextStep() {
		if (packedWithFood) {
			if (path.size() >= 2)
				return path.get(path.size()-2);
			else if (path.size() == 1) {
				return path.get(path.size() -1);
			} else 
				throw new NoSuchElementException("no more of the path left to move!");
		}
		return calculateNextStepCoordinatesForCurrentDirection();
	}
	
	public void swapWith(Ant otherAnt) {
		if (otherAnt.nextStep().equals(currentPosition()) &&
			nextStep().equals(otherAnt.currentPosition()) &&
			this.packedWithFood &&
			otherAnt.packedWithFood) {
			otherAnt.path.remove(otherAnt.path.size() - 1);
			path.remove(path.size() -1);
		} else {
			throw new IllegalStateException("only colliding ants on their way back can swap their positions!");
		}
	}

	public String printToBoard() {
		return packedWithFood ? "+" : "*";
	}
	
	public void takeFoodUnitFrom(FoodCell foodCell) {
		this.packedWithFood = true;
		foodCell.takeFoodUnit();
	}

	public boolean readyToDeliverFood() {
		return packedWithFood;
	}
	
	public boolean equals(Object other) {
		if (other instanceof Ant) {
			return ((Ant)other).antId.equals(antId);
		}
		return false;
	}

	private Coordinates calculateNextStepCoordinatesForCurrentDirection() {
		Coordinates currentPosition = currentPosition();
		switch(currentDirection) {
		case N:
			return currentPosition.shiftWith(0, -1);
		case NE:
			return currentPosition.shiftWith(1, -1);
		case E:
			return currentPosition.shiftWith(1, 0);
		case SE:
			return currentPosition.shiftWith(1, 1);
		case S:
			return currentPosition.shiftWith(0, 1);
		case SW:
			return currentPosition.shiftWith(-1, 1);
		case W:
			return currentPosition.shiftWith(-1, 0);
		case NW:
			return currentPosition.shiftWith(-1, -1);
		default:
			throw new IllegalArgumentException(
					String.format("no action found for: %s", currentDirection));
		}
	}
	
	private void turnToNewDirection() {
		AntMovingDirection nextDirection = AntMovingDirection.random();
		while(currentDirection == nextDirection) {			
			nextDirection = AntMovingDirection.random();
		}
		currentDirection = nextDirection;
	}
}
