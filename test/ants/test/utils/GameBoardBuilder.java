package ants.test.utils;
import ants.ants.Ant;
import ants.ants.AntMovingDirection;
import ants.board.GameBoard;
import ants.board.cells.AntHillCell;
import ants.board.cells.Coordinates;
import ants.board.cells.FoodCell;
import ants.board.cells.GameCell;
import ants.board.dimension.BoardDimension;

public class GameBoardBuilder {
	public static GameBoardBuilder createEmptyGameBoardIn(BoardDimension dimension) {
		GameBoard gameBoard = new GameBoard(dimension);
		for(Coordinates coordinates : dimension.createCoordinates())
			gameBoard.placeCell(GameCell.empty(), coordinates);
		return new GameBoardBuilder(gameBoard);
	}
	
	private GameBoard board;
	
	public GameBoardBuilder(GameBoard board) {
		this.board = board;
	}
	
	public GameBoardBuilder placeAntHillAt(Coordinates antHillCoordinates, int antHillFoodCount) {
		board.placeAntHillAt(new AntHillCell(antHillFoodCount, antHillCoordinates), antHillCoordinates);
		return this;
	}
	
	public GameBoardBuilder placeFoodCellAt(Coordinates foodCellCoordinates, int foodCount) {
		board.placeCell(new FoodCell(foodCount), foodCellCoordinates);
		return this;
	}
	
	public GameBoardBuilder placeAnt(Ant ant) {
		board.placeAnt(ant);
		return this;
	}
	
	public GameBoardBuilder placeAnt(Coordinates coordinates) {
		board.placeAnt(new Ant(coordinates));
		return this;
	}
	
	public GameBoardBuilder placeAnt(Coordinates coordinates, AntMovingDirection fixedStartingDirection) {
		board.placeAnt(new Ant(coordinates, fixedStartingDirection));
		return this;
	}
	
	public GameBoard build() {
		return board;
	}
}
