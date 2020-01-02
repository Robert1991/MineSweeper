package ants.board.dimension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ants.ants.Ant;
import ants.board.GameBoard;
import ants.board.cells.AntHillCell;
import ants.board.cells.Coordinates;
import ants.board.cells.GameCell;
import ants.random.RandomWrapper;
import ants.runner.GameConfiguration;

public class BoardDimension {
	public RandomWrapper randomWrapper = new RandomWrapper();
	private int xDim;
	private int yDim;
	
	public static BoardDimension of(int xDim, int yDim) {
		return new BoardDimension(xDim, yDim);
	}

	public BoardDimension(int xDim, int yDim) {
		this.xDim = xDim;
		this.yDim = yDim;
	}
	
	public BoardDimension with(RandomWrapper randomWrapper) {
		this.randomWrapper = randomWrapper;
		return this;
	}
	
	public GameObjectPositions randomPositionsFor(GameConfiguration gameConfiguration) {
		List<Integer> foodCellPositions = new ArrayList<Integer>();
		int antHillPosition = nextRandomPositionInDimension(-1, foodCellPositions);
		gameConfiguration.forFoodCellCount(
				() -> foodCellPositions.add(nextRandomPositionInDimension(antHillPosition, foodCellPositions)));
		return new GameObjectPositions(antHillPosition, foodCellPositions);
	}

	public boolean smallerThan(BoardDimension other) {
		return this.xDim < other.xDim || this.yDim < other.yDim;
	}

	public List<Coordinates> createCoordinates() {
		List<Coordinates> coordinates = new ArrayList<Coordinates>();
		for (int yCoordinate = 1; yCoordinate <= yDim; yCoordinate++) {
			for (int xCoordinate = 1; xCoordinate <= xDim; xCoordinate++)
				coordinates.add(new Coordinates(xCoordinate, yCoordinate));
		}
		
		return coordinates;
	}

	public boolean symmetrical() {
		return xDim == yDim;
	}

	public String printStateOf(GameBoard gameBoard) {
		final List<Ant> antsInGame = gameBoard.antsInGame();
		final StringBuilder gameBoardStateStringBuilder = new StringBuilder();
		gameBoard.doForAllGameBoardCells((coordinates, cell) -> {
			gameBoardStateStringBuilder.append(printCell(coordinates, cell, antsInGame));
		    if (liesOnXBorder(coordinates))
		    	gameBoardStateStringBuilder.append("\n");
		});
		return gameBoardStateStringBuilder.toString();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof BoardDimension) {
			BoardDimension otherBoardDimension = (BoardDimension)other;
			return otherBoardDimension.xDim == this.xDim &&
				   otherBoardDimension.yDim == this.yDim;
		}
		return false;
	}

	private boolean liesOnXBorder(Coordinates coordinates) {
		if (coordinates.xCoordinate == xDim)
			return true;
		return false;
	}
	
	private int nextRandomPositionInDimension(int antHillPosition, List<Integer> alreadyTakenFoodCellPositions) {
		int nextInt = -1;
		do {
			nextInt = randomWrapper.randIntIn(1, xDim * yDim);
		} while(nextInt == antHillPosition || alreadyTakenFoodCellPositions.contains(nextInt));
		return nextInt;
	}
	
	private String printCell(Coordinates cellCoordinates, GameCell cell, List<Ant> antsInGame) {
		if (cell instanceof AntHillCell)
			return cell.printOut();
		else {
			Optional<Ant> possibleAnt = antsInGame.stream().filter(ant -> ant.currentPosition().equals(cellCoordinates))
												   		   .findFirst();
			if (possibleAnt.isPresent())
				return possibleAnt.get().printToBoard();
			return cell.printOut();
		}
	}
}
