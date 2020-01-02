package ants.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.BiConsumer;

import ants.ants.Ant;
import ants.board.cells.AntHillCell;
import ants.board.cells.Coordinates;
import ants.board.cells.FoodCell;
import ants.board.cells.GameCell;
import ants.board.dimension.BoardDimension;

public class GameBoard {
	private static BoardDimension MINIMUM_BOARD_DIMENSION = new BoardDimension(3, 3);
	private BoardDimension dimension;
	private TreeMap<Coordinates, GameCell> gameBoard = new TreeMap<Coordinates, GameCell>();
	private AntHillCell antHill;
	private List<Ant> antsInGame = new ArrayList<Ant>();
	private int currentIteration = 1;
	
	public GameBoard(BoardDimension dimension) {
		if (dimension.smallerThan(MINIMUM_BOARD_DIMENSION))
			throw new IllegalArgumentException("the game field needs at least a dimension of 3x4 or 4x3");
		if (dimension.symmetrical())
			throw new IllegalArgumentException("board dimension can not be symmetrical for ant simulation");
		this.dimension = dimension;
	}

	public void iterate() {
		removeEmptyFoodCellsIfNecassary();
		letAntEnterHillWhenOneIsThereWithFood();		
		moveAntsInGame();
		releaseAntsIfPossible();
		printGameBoardState();
		currentIteration++;
	}
	
	public int foodUnitsInTransportation() {
		return (int)antsInGame.stream().filter(ant -> ant.readyToDeliverFood())
								  	   .count();
	}
	
	public boolean stillFoodToCollect() {
		return antsInGame.size() > 0 ||
			   stillFoodLeftToCollectForTheNextAnt();
	}
	
	public void doForAllGameBoardCells(BiConsumer<Coordinates, GameCell> cellFunction) {
		gameBoard.entrySet().forEach(cell -> cellFunction.accept(cell.getKey(), cell.getValue()));
	}
	
	public List<Ant> antsInGame() {
		return antsInGame;
	}
	
	public int foodUnitsLeft() {
		return gameBoard.values()
				 .stream()
				 .filter(cell -> cell instanceof FoodCell)
				 .mapToInt(cell -> ((FoodCell)cell).currentFoodCount())
				 .sum();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof GameBoard)
			return new GameBoardComparator((GameBoard)other).areEqual();
		return false;
	}

	// builder methods
	public void placeAntHillAt(AntHillCell antHill, Coordinates antHillCoordinates) {
		this.antHill = antHill;
		this.gameBoard.put(antHillCoordinates, antHill);
	}
	
	public void placeCell(GameCell cell, Coordinates cellCoordinates) {
		gameBoard.put(cellCoordinates, cell);
	}
	
	public void placeAnt(Ant ant) {
		this.antsInGame.add(ant);
	}
	//
	
	private void removeEmptyFoodCellsIfNecassary() {
		for(Entry<Coordinates, GameCell> gameField : gameBoard.entrySet()) {
			if (gameField.getValue() instanceof FoodCell)
				if (((FoodCell)gameField.getValue()).isEmpty())
					gameBoard.put(gameField.getKey(), GameCell.empty());
		}
	}
	
	private void moveAntsInGame() {
		List<Ant> alreadyMovedDueToSwap = new ArrayList<Ant>();
		for(Ant ant : antsInGame) {
			if (!alreadyMovedDueToSwap.contains(ant)) {
				if (antCanMoveTo(ant.nextStep())) {
					if (antMovedToFoodCell(ant.move())) {
						ant.takeFoodUnitFrom((FoodCell)gameBoard.get(ant.currentPosition()));
					}
				} else {
					letAntTurnWaitOrSwap(alreadyMovedDueToSwap, ant);
				}
			}
		}
	}
	
	private void releaseAntsIfPossible() {
		boolean stillFoodLeft = stillFoodLeftToCollectForTheNextAnt();
		if (antHill.antCanBeReleased() && 
		    antHillIsNotBlockedByOtherAnt() &&
		    stillFoodLeft) {
			antsInGame.add(antHill.releaseNextAnt());
		}
	}
	
	private void printGameBoardState() {
		System.out.println(dimension.printStateOf(this));
		System.out.println("---------------------------");
		System.out.println(String.format("Ants in game: %d | Food units left on board: %d | "
				+ "Food units in ant hill: %d | Ants in hill: %d\n"
				+ "Food units in transportation: %d | Iteration: %d",
				antsInGame.size(),
				foodUnitsLeft(),
				antHill.foodUnitsDelivered(),
				antHill.antsLeft(),
				foodUnitsInTransportation(),
				currentIteration));
	}

	private boolean stillFoodLeftToCollectForTheNextAnt() {
		if (antsInGame.size() == 0)
			return foodUnitsLeft() > 0;
		else {
			if (antsInGame.size() < foodUnitsLeft()) {
				return true; 
			} else if (foodUnitsLeft() == antsInGame.size()) {
				return antsInGame.stream().filter(ant -> ant.readyToDeliverFood())
										  .count() > 0L;
			} else {
				return false;
			}
		}
	}
	
	private boolean antHillIsNotBlockedByOtherAnt() {
		return antsInGame.stream()
				.filter(ant -> antHill.isAntOccupingHill(ant))
				.count() == 0L;
	}
	
	private void letAntEnterHillWhenOneIsThereWithFood() {
		Optional<Ant> antReadyToDeliver = antsInGame.stream()
				.filter(ant -> antHill.isAntOccupingHill(ant) &&
						ant.readyToDeliverFood())
				.findFirst();
		if (antReadyToDeliver.isPresent()) {
			antHill.antCameBackWithFood();
			antsInGame.remove(antReadyToDeliver.get());
		}
	}
	
	private void letAntTurnWaitOrSwap(List<Ant> alreadyMovedDueToSwap, Ant ant) {
		if (ant.readyToDeliverFood()) {
			Optional<Ant> returningAntStandingInTheWay = checkIfARetruningAntIsStandingInTheWayOf(ant);
			if (returningAntStandingInTheWay.isPresent()) {
				ant.swapWith(returningAntStandingInTheWay.get());
				alreadyMovedDueToSwap.add(returningAntStandingInTheWay.get());
			} else {
				ant.turnOrWait();
			}
		} else {					
			ant.turnOrWait();
		}
	}
	
	private Optional<Ant> checkIfARetruningAntIsStandingInTheWayOf(Ant antWantingToMove) {
		return antsInGame.stream()
						 .filter(antOnBoard -> !antOnBoard.equals(antWantingToMove))
						 .filter(other -> other.readyToDeliverFood())
						 .filter(other -> other.currentPosition().equals(antWantingToMove.nextStep()))
						 .filter(other -> other.nextStep().equals(antWantingToMove.currentPosition()))
						 .findFirst();
	}
	
	private boolean antCanMoveTo(Coordinates nextCoordinates) {
		if (coordinatesInBoard(nextCoordinates)) {
			if (nextCellIsNotOccupiedAlready(nextCoordinates))
				return true;
		}
		return false;
	}

	private boolean coordinatesInBoard(Coordinates lookedUp) {
		return gameBoard.keySet().stream()
						  .filter(onBoard -> onBoard.equals(lookedUp))
						  .count() > 0L;
	}
	
	private boolean nextCellIsNotOccupiedAlready(Coordinates nextCoordinates) {
		return antsInGame.stream().filter(ant -> ant.currentPosition().equals(nextCoordinates))
						   		  .count() == 0L;
	}
	
	private boolean antMovedToFoodCell(Coordinates move) {
		return gameBoard.get(move) instanceof FoodCell;
	}
	
	class GameBoardComparator {
		private GameBoard other;

		GameBoardComparator(GameBoard other) {
			this.other = other;
		}
		
		boolean areEqual() {
			if (other.dimension.equals(dimension)) {
				if (gameBoardsAreEqual()) {
					if (other.antsInGame.size() == antsInGame.size()) {
						return checkIfAntsPositionsAreEqual();
					}
				}
			}
			return false;
		}
		
		private boolean gameBoardsAreEqual() {
			for(Entry<Coordinates, GameCell> entry : gameBoard.entrySet()) {
				if (!other.gameBoard.containsKey(entry.getKey())) {
					return false;
				} else {
					if(!other.gameBoard.get(entry.getKey())
										  		 .equals(entry.getValue())) {
						return false;
					}
				}
			}
			
			return true;
		}
		
		private boolean checkIfAntsPositionsAreEqual() {
			for(Ant ant : antsInGame) {
				Optional<Ant> antInSamePosOnOtherBoard = other.antsInGame.stream()
										 .filter(otherAnt -> otherAnt.currentPosition().equals(ant.currentPosition()))
										 .findFirst();
				if (!antInSamePosOnOtherBoard.isPresent())
					return false;
			}
			
			return true;
		}
	}
}
